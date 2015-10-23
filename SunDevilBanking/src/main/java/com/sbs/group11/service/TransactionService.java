package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;

public interface TransactionService {

	void addTransaction(Transaction transaction);
	
	String getUniqueTransactionID();
	
	BigDecimal getBigDecimal(String accNumber);
	
	List<StatementMonthYear> getStatementMonths(String accNumber);
	
	List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year);
	
	Transaction getTransaction(String transactionID); 
	
	List<Transaction> getPendingTransactions();
	
	void approveTransaction(Transaction transaction);
	
	void deleteTransaction(Transaction transaction);
	
	void modifyTransaction(Transaction transaction);
	
}
