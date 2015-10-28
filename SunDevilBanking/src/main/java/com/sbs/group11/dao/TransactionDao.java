package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;

public interface TransactionDao {

	void addTransaction(Transaction transaction);	
	
	boolean isUniqueTransactionID(String transactionID);
	
	boolean isCriticalTransaction(Transaction transaction);
	
	Transaction getTransactionByID(String transactionID);
	
	List<Transaction> getPendingTransactions();
	
	public List<Transaction> getPendingCriticalTransactions();
	
	boolean approveTransaction(Transaction transaction);
	
	void modifyTransaction(Transaction transaction);
	
	void declineTransaction(Transaction transaction);
	
	List<StatementMonthYear> getStatementMonths(String accNumber);
	
	List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year);
	
	List<Transaction> getTransactionsForAccountNumber(String accNumber);
}
