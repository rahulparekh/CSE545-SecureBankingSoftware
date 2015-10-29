package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;

public interface TransactionDao {

	void addTransaction(Transaction transaction);
	
	void updateTransaction(Transaction transaction);
	
	boolean isUniqueTransactionID(String transactionID);
	
	Transaction getTransactionByID(String transactionID);
	
	List<Transaction> getPendingTransactions();
	
	public List<Transaction> getPendingCriticalTransactions();
	
	public boolean isTransactionPending(String transactionID);
	
	// boolean approveTransaction(Transaction transaction);
	
	void modifyTransaction(Transaction transaction);
	
	void declineTransaction(Transaction transaction);
	
	List<StatementMonthYear> getStatementMonths(String accNumber);
	
	List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year);

	Transaction getTransactionByPairId(String pairId, String transactionID);
	
	List<Transaction> getTransactionsForAccountNumber(String accNumber);
}
