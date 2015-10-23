package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import com.sbs.group11.model.Transaction;

public interface TransactionService {

	void addTransaction(Transaction transaction);
	
	String getUniqueTransactionID();
	BigDecimal getBigDecimal(String number);
	Transaction getTransaction(String transactionID); 
	List<Transaction> getPendingTransactions();
	void approveTransaction(Transaction transaction);
	void deleteTransaction(Transaction transaction);
	void modifyTransaction(Transaction transaction);
}
