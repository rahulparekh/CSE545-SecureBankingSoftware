package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

public interface TransactionService {

	void addTransaction(Transaction transaction);

	String getUniqueTransactionID();

	BigDecimal getBigDecimal(String accNumber);

	List<StatementMonthYear> getStatementMonths(String accNumber);

	List<Transaction> getCompletedTransactionsByAccountNummber(
			String accNumber, String month, int year);

	Transaction getTransaction(String transactionID);

	List<Transaction> getPendingTransactions();

	public List<Transaction> getPendingCriticalTransaction();
	
	boolean approveTransactionafterModification(Transaction transaction);
	boolean approveTransaction(String transactionID);
	
	void declineTransaction(String transactionID);
	
	void modifyTransaction(Transaction transaction);

	boolean isTransferAccountValid(AccountService accountService,
			TransactionService transactionService,
			List<Account> senderAccounts,
			HttpServletRequest request,
			ModelMap model, User user, RedirectAttributes attr);
	
	List<Transaction> getTransactionsForAccountNumber(String accNumber);

}
