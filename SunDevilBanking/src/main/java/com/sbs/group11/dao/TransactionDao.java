package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;

public interface TransactionDao {

	void addTransaction(Transaction transaction);	
	boolean isUniqueTransactionID(String transactionID);
	List<StatementMonthYear> getStatementMonths(String accNumber);
	
}
