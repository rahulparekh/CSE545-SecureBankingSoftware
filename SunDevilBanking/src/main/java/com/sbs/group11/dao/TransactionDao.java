package com.sbs.group11.dao;

import com.sbs.group11.model.Transaction;

public interface TransactionDao {

	void addTransaction(Transaction transaction);
	
	boolean isUniqueTransactionID(String transactionID);
	
}
