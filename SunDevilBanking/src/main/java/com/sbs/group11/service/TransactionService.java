package com.sbs.group11.service;

import java.math.BigDecimal;

import com.sbs.group11.model.Transaction;

public interface TransactionService {

	void addTransaction(Transaction transaction);
	
	String getUniqueTransactionID();
	BigDecimal getBigDecimal(String number);
}
