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

import com.sbs.group11.dao.TransactionDao;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {
	final static Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionDao dao;

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
		} catch (NumberFormatException nfe) {
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
	
	public void approveTransaction(Transaction transaction){
		
		String approved="3";
		transaction.setStatus(approved);
		// TODO: call Dao for approving
		
		
	}
	
	public void deleteTransaction(Transaction transaction){
		
	}
	
	public void modifyTransaction(Transaction transaction){
		
	}

	public List<StatementMonthYear> getStatementMonths(String accNumber) {
		return dao.getStatementMonths(accNumber);
	}

	public List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year) {
		return dao.getCompletedTransactionsByAccountNummber(accNumber, month, year);
	}

	public boolean isTransferAccountValid(AccountService accountService,
			TransactionService transactionService,
			List<Account> receiverAccounts, List<Account> senderAccounts,
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
			
			Account senderAccount = accountService.getValidAccountByNumber(
					request.getParameter("senderAccNumber"), senderAccounts);
			
			if (senderAccount == null) {
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
		logger.debug("Receiver account number: " + request
				.getParameter("receiverAccNumber"));
		Account receiverAccount = accountService.getAccountByNumber(request
						.getParameter("receiverAccNumberExternal"));
		
		if( receiverAccount != null && !receiverAccount.toString().isEmpty() ) {
			logger.debug("Receiver Account: " + receiverAccount.toString());
			return true;
		}		
		
		logger.debug("receiverAccount was not returned");
		return false;
	}

}
