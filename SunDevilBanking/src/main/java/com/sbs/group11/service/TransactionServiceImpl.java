/*
 * 
 */
package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.dao.AccountDao;
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
	private AccountDao accountDao;

	@Autowired
	private PaymentRequestDao paymentRequestDao;

	public void addTransaction(Transaction transaction) {

		transaction.setIsCritical(isCritical(transaction.getAmount(),
				CRITICAL_VALUE));

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

	public Transaction getTransaction(String transactionID) {

		logger.info("inside service");
		return dao.getTransactionByID(transactionID);

	}

	public List<Transaction> getPendingTransactions() {

		return dao.getPendingTransactions();

	}

	public List<Transaction> getPendingCriticalTransaction() {

		return dao.getPendingCriticalTransactions();
	}

	public boolean approveTransaction(Transaction transaction) {

		logger.debug("inside approve transaction");

		if (!dao.isTransactionPending(transaction.getTransactionID())) {
			return false;
		}

		logger.debug("Dao pending");

		Account senderAcc = accountDao.findByAccountNumber(transaction
				.getSenderAccNumber());
		Account recieverAcc = accountDao.findByAccountNumber(transaction
				.getReceiverAccNumber());

		logger.debug("Got accounts");

		BigDecimal amount = transaction.getAmount();
		BigDecimal zero = BigDecimal.ZERO;
		LocalDateTime now = new DateTime().toLocalDateTime();

		logger.debug("amount.compare: " + amount.compareTo(zero)
				+ " amount:balance " + amount.compareTo(senderAcc.getBalance()));

		// Means credit debit type of transactions
		if (senderAcc.getNumber().equals(recieverAcc.getNumber())) {
			if (transaction.getType().equalsIgnoreCase("credit")) {
				senderAcc.setBalance(senderAcc.getBalance().add(amount));
			} else if(amount.compareTo(zero) == 1 && senderAcc.getBalance().compareTo(
					amount) == 1) {
				senderAcc.setBalance(senderAcc.getBalance().subtract(amount));
			
			} else {
				return false;
			}
			
			transaction.setBalance(senderAcc.getBalance());
			transaction.setStatus("approved");
			senderAcc.setUpdatedAt(now);
			transaction.setUpdatedAt(now);
			updateTransaction(transaction);

			return true;

		}

		// For other transactions
		if ((amount.compareTo(zero) == 1 && senderAcc.getBalance().compareTo(
				amount) == 1)) {

			senderAcc.setBalance(senderAcc.getBalance().subtract(amount));
			senderAcc.setUpdatedAt(now);
			recieverAcc.setBalance(recieverAcc.getBalance().add(amount));
			recieverAcc.setUpdatedAt(now);

			if (transaction.getType().equalsIgnoreCase("credit")) {
				transaction.setBalance(recieverAcc.getBalance());
				updateTransactionPair(transaction, "approved",
						senderAcc.getBalance());
			} else {
				transaction.setBalance(senderAcc.getBalance());
				updateTransactionPair(transaction, "approved",
						recieverAcc.getBalance());
			}

			transaction.setStatus("approved");
			transaction.setUpdatedAt(now);
			updateTransaction(transaction);
			accountService.updateAccount(senderAcc);
			accountService.updateAccount(recieverAcc);

			return true;

		}

		return false;
	}

	public boolean updateTransactionPair(Transaction transaction,
			String status, BigDecimal balance) {

		String pairId = transaction.getPairId();

		if (pairId != null) {

			Transaction transactionPair = getTransactionByPairId(pairId,
					transaction.getTransactionID());

			if (transactionPair == null) {
				return false;
			}

			logger.debug("Found the pair: " + transactionPair);

			if (status.equalsIgnoreCase("approved") && balance != null) {

				if (transactionPair.getType().equalsIgnoreCase("credit")) {
					transactionPair.setBalance(balance);
				} else {
					transactionPair.setBalance(balance);
				}

			}

			transactionPair.setStatus(status);
			transactionPair.setUpdatedAt(new DateTime().toLocalDateTime());
			logger.debug(transactionPair);
			updateTransaction(transactionPair);

			logger.debug("Updated the pair");
			return true;

		} else {
			return false;
		}

	}

	public boolean approveTransactionafterModification(Transaction transaction) {
		return approveTransaction(transaction);
	}

	public void declineTransaction(String transactionID) {

		String status = "declined";
		Transaction transaction = getTransaction(transactionID);
		transaction.setStatus(status);
		dao.declineTransaction(transaction);

	}

	public void modifyTransaction(Transaction transaction) {

	}

	public List<StatementMonthYear> getStatementMonths(String accNumber) {
		return dao.getStatementMonths(accNumber);
	}

	public List<Transaction> getCompletedTransactionsByAccountNummber(
			String accNumber, String month, int year) {
		return dao.getCompletedTransactionsByAccountNummber(accNumber, month,
				year);
	}

	public boolean isTransferAccountValid(List<Account> accounts,
			HttpServletRequest request, ModelMap model, User user,
			RedirectAttributes attr) {

		// null pointer checks
		if (request.getParameter("type") == null
				|| request.getParameter("receiverAccNumber") == null
				|| request.getParameter("senderAccNumber") == null) {
			logger.debug("Null checks returned null!");
			return false;
		}

		// get the transfer type
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			logger.debug("Internal Transfer inside valid transfer");

			logger.debug("Receiver account number: "
					+ request.getParameter("receiverAccNumber"));
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
		Account receiverAccountExt = accountService.getAccountByNumber(request
				.getParameter("receiverAccNumberExternal"));

		if (receiverAccountExt != null
				&& !receiverAccountExt.toString().isEmpty()) {

			// Check if the external account belongs to the current user
			// Then external transfer doesnt apply at all
			for (Account userAccount : accounts) {
				if (userAccount.getNumber().equals(
						receiverAccountExt.getNumber())) {
					return false;
				}
			}

			logger.debug("Receiver Account: " + receiverAccountExt.toString());
			return true;
		}

		logger.debug("receiverAccount was not returned");
		return false;
	}

	public List<Transaction> getTransactionsForAccountNumber(String accNumber) {

		return dao.getTransactionsForAccountNumber(accNumber);

	}

	public void initiatePayment(PaymentRequest paymentRequest) {

		paymentRequestDao.savePaymentRequest(paymentRequest);
		String content = null;
		String email = null;

		// check if the user has initiated the payment
		// or merchant has
		if (paymentRequest.getUserAccepted() == 1) {

			email = accountService
					.getAccountByNumber(paymentRequest.getMerchantAccNumber())
					.getUser().getEmail();
		} else {
			email = accountService
					.getAccountByNumber(paymentRequest.getCustomerAccNumber())
					.getUser().getEmail();
		}

		// send email to user
		content = "A new payment request has been made. "
				+ "To process the payment, please go to "
				+ "https://group11.mobicloud.asu.edu/home/merchant-payment-requests.\n\n"
				+ "The payment request will expire in 2 hours from now.\n\n"
				+ "Please use the following OTP to accept the payment: "
				+ paymentRequest.getOtp() + "\n\n"
				+ "You can accept the payment or let it expire.";

		// send email
		emailService.sendEmail(email, "Sun Devil Banking. New Payment Request",
				content);

	}

	public void acceptPayment(PaymentRequest paymentRequest) {
		String senderAccNumber = paymentRequest.getSenderAccNumber();
		String receiverAccountNumber = paymentRequest.getReceiverAccNumber();

		paymentRequest.setUserAccepted(1);
		paymentRequest.setMerchantAccepted(1);

		// update the payment request
		paymentRequestDao.savePaymentRequest(paymentRequest);

		String isCritical = isCritical(paymentRequest.getAmount(),
				CRITICAL_VALUE);

		String pairId = UUID.randomUUID().toString();

		// Create two transactions
		Transaction transaction1 = new Transaction(getUniqueTransactionID(),
				"Payment From " + senderAccNumber + " To: "
						+ receiverAccountNumber, receiverAccountNumber,
				senderAccNumber, "pending", "Debit",
				paymentRequest.getAmount(), isCritical, senderAccNumber, pairId);

		Transaction transaction2 = new Transaction(getUniqueTransactionID(),
				"Payment From " + senderAccNumber + " To: "
						+ receiverAccountNumber, receiverAccountNumber,
				senderAccNumber, "pairtxn", "Credit",
				paymentRequest.getAmount(), isCritical, receiverAccountNumber,
				pairId);

		addTransaction(transaction1);
		addTransaction(transaction2);

	}

	public List<PaymentRequest> getPaymentsByAccNumber(String accNumber,
			int initiatedBy) {
		return paymentRequestDao.getPaymentsByAccNumber(accNumber, initiatedBy);
	}

	public String isCritical(BigDecimal amount, BigDecimal critical_value) {

		if (amount != null && amount.compareTo(critical_value) >= 0) {
			logger.debug("Critical transaction with critical value "
					+ critical_value.toString() + " and amount "
					+ amount.toString());
			return "Yes";
		}

		return "No";
	}

	public PaymentRequest getPaymentRequest(int id) {
		return paymentRequestDao.getPaymentRequest(id);
	}

	public Transaction getTransactionByPairId(String pairId,
			String transactionID) {
		return dao.getTransactionByPairId(pairId, transactionID);
	}

	public void updateTransaction(Transaction transaction) {

		dao.updateTransaction(transaction);

	}
}
