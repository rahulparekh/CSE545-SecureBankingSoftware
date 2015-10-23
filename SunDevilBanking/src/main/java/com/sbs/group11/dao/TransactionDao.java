package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.Transaction;

public interface TransactionDao {

	void addTransaction(Transaction transaction);
	
	boolean isUniqueTransactionID(String transactionID);
	
	Transaction getTransactionByID(String transactionID);
	
	List<Transaction> getPendingTransactions();
	
	void approveTransaction(Transaction transaction);
	
	void modifyTransaction(Transaction transaction);
	
	void deleteTransaction(Transaction transaction);
	
}