/*
 * 
 */
package com.sbs.group11.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.dao.PaymentRequestDao;
import com.sbs.group11.dao.TransactionDao;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.PaymentRequest;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {
	final static Logger logger = Logger.getLogger(TransactionServiceImpl.class);
	final private BigDecimal CRITICAL_VALUE = new BigDecimal(500);
	
	@Autowired
	private AccountService accountService;
		
	@Autowired
	private SendEmailService emailService;

	@Autowired
	private TransactionDao dao;
	
	@Autowired
	private PaymentRequestDao paymentRequestDao;

	public void addTransaction(Transaction transaction) {
		dao.addTransaction(transaction);
	}

	public String getUniqueTransactionID() {
		// get 17 digit unique id
		String transactionID = UUID.randomUUID().toString().replace("-", "")
				.substring(0, 17);
		logger.debug("Generated transaction ID: " + transactionID);

		// check against the DB
		logger.info(dao.isUniqueTransactionID(transactionID));
		if (!dao.isUniqueTransactionID(transactionID)) {
			logger.debug("Not a unique transaction ID: " + transactionID);
			transactionID = getUniqueTransactionID();
		}

		logger.debug("Unique ID generated: " + transactionID);
		return transactionID;
	}

	public BigDecimal getBigDecimal(String number) {
		try {
			return new BigDecimal(number);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public Transaction getTransaction(String transactionID){
		
		System.out.println("inside service");
		return dao.getTransactionByID(transactionID);
		
	}
	
	public List<Transaction> getPendingTransactions(){
		
	  return dao.getPendingTransactions();
		
	}
	
	public List<Transaction> getPendingCriticalTransaction(){
		
		return dao.getPendingCriticalTransactions();
	}
	
	public boolean approveTransaction(String transactionID){
		
		Transaction transaction = getTransaction(transactionID);
		return dao.approveTransaction(transaction);
		
		
		
	}
	
	public void declineTransaction(String transactionID){
		
		String status="declined";
		Transaction transaction = getTransaction(transactionID);
		transaction.setStatus(status);
		dao.declineTransaction(transaction);
		
		
		
	}
	
	public void modifyTransaction(Transaction transaction){
		
	}

	public List<StatementMonthYear> getStatementMonths(String accNumber) {
		return dao.getStatementMonths(accNumber);
	}

	public List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year) {
		return dao.getCompletedTransactionsByAccountNummber(accNumber, month, year);
	}

	public boolean isTransferAccountValid(List<Account> accounts,
			HttpServletRequest request, ModelMap model, User user, RedirectAttributes attr) {
		
		// null pointer checks
		if (request.getParameter("type") == null
			|| request.getParameter("receiverAccNumber") == null
			|| request.getParameter("senderAccNumber") == null ) {
			logger.debug("Null checks returned null!");
			return false;
		}
		
		
		// get the transfer type
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			logger.debug("Internal Transfer inside valid transfer");
			
			logger.debug("Receiver account number: " + request
					.getParameter("receiverAccNumber"));
			Account receiverAccount = accountService.getAccountByNumber(request
							.getParameter("receiverAccNumber"));
			
			
			if (receiverAccount == null) {
				logger.warn("Someone tried fund transfer functionality for some other account. Details:");
				logger.warn("Credit/Debit Acc No: "
						+ request.getParameter("senderAccNumber"));
				logger.warn("Customer ID: " + user.getCustomerID());
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return false;
			}
			
			return true;
		}
		
		// Exit the transaction if Account to be transferred to doesn't exist
		Account receiverAccountExt = accountService.getAccountByNumber(
				request.getParameter("receiverAccNumberExternal"));
		
		if( receiverAccountExt != null 
				&& !receiverAccountExt.toString().isEmpty() ) {
			
			// Check if the external account belongs to the current user
			// Then external transfer doesnt apply at all
			for(Account userAccount : accounts) {
				if( userAccount.getNumber().equals(receiverAccountExt.getNumber())) {
					return false;
				}
			}
			
			logger.debug("Receiver Account: " + receiverAccountExt.toString());
			return true;
		}		
		
		logger.debug("receiverAccount was not returned");
		return false;
	}

	public void initiatePayment(PaymentRequest paymentRequest) {
		
		paymentRequestDao.savePaymentRequest(paymentRequest);
		String content = null;
		String email = null;
		
		// check if the user has initiated the payment
		// or merchant has
		if ( paymentRequest.getUserAccepted() == 1 ) {
			
			content = "A new payment request has been made. "
					+ "To process the payment, please go to "
					+ "https://group11.mobicloud.asu.edu/home/merchant-payment-requests.\n\n"
					+ "The payment request will expire in 2 hours from now.\n\n"
					+ "Please use the following OTP to accept the payment: " + paymentRequest.getOtp();
			
			email = accountService
							.getAccountByNumber(paymentRequest.getMerchantAccNumber())
							.getUser()
							.getEmail();
		}
		
		// send email to user
		content = "A new payment request has been made. "
				+ "To process the payment, please go to "
				+ "https://group11.mobicloud.asu.edu/home/merchant-payment-requests.\n\n"
				+ "The payment request will expire in 2 hours from now.\n\n"
				+ "Please use the following OTP to accept the payment: " + paymentRequest.getOtp();
		
		email = accountService
						.getAccountByNumber(paymentRequest.getCustomerAccNumber())
						.getUser()
						.getEmail();
		
		// send email
		emailService.sendEmail(email, "Sun Devil Banking. New Payment Request", content);
		
		
	}

	public void acceptPayment(PaymentRequest paymentRequest) {
		String senderAccount = null;
		String receiverAccount = null;
		String type1 = null;
		String type2 = null;
		
		// update the payment request
		paymentRequestDao.savePaymentRequest(paymentRequest);
		
		String isCritical = isCritical(paymentRequest.getAmount(),
				CRITICAL_VALUE);
		
		// 0 customer, 1 merchant
		if (paymentRequest.getInitiatedBy() == 0 || paymentRequest.getType().equalsIgnoreCase("debit")) {
			senderAccount = paymentRequest.getCustomerAccNumber();
			receiverAccount = paymentRequest.getMerchantAccNumber();
		} else {
			senderAccount = paymentRequest.getMerchantAccNumber();
			receiverAccount = paymentRequest.getCustomerAccNumber();
		}
		
		if (paymentRequest.getType().equalsIgnoreCase("credit")) {
			type1 = "Credit";
			type2 = "Debit";
		} else {
			type2 = "Credit";
			type1 = "Debit";
		}
			
		
		// Create two transactions
		Transaction transaction1 = new Transaction(
				getUniqueTransactionID(), 
				"Payment From " + senderAccount + " To: " + receiverAccount, 
				senderAccount, 
				receiverAccount,
				"pending", 
				type1, 
				paymentRequest.getAmount(), 
				isCritical,
				senderAccount);
		
		Transaction transaction2 = new Transaction(
				getUniqueTransactionID(), 
				"Payment From " + senderAccount + " To: " + receiverAccount, 
				senderAccount, 
				receiverAccount,
				"pending", 
				type2, 
				paymentRequest.getAmount(), 
				isCritical,
				receiverAccount);
		
		addTransaction(transaction1);
		addTransaction(transaction2);
		
	}

	public void completePayment(PaymentRequest paymentRequest) {
		// TODO Auto-generated method stub
		
	}

	public List<PaymentRequest> getPaymentsByAccNumber(String accNumber, int initiatedBy) {
		return paymentRequestDao.getPaymentsByAccNumber(accNumber, initiatedBy);
	}

	public String isCritical(BigDecimal amount, BigDecimal critical_value) {
		
		if (amount != null && amount.compareTo(critical_value) >= 0) {
			logger.debug("Critical transaction!");
			return "Yes";
		}
		
		return "No";
	}

}
