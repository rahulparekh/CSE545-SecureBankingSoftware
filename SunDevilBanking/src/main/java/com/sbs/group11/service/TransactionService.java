package com.sbs.group11.service;

import com.sbs.group11.model.Transaction;

public interface TransactionService {

	void addTransaction(Transaction transaction);
	
	String getUniqueTransactionID();
	
}
