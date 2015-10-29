package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;

public interface AccountService {
	List<Account> getAccountsByCustomerID(String customerID);

	Account getAccountByNumber(String accNumber);

	Account getValidAccountByNumber(String accNumber, List<Account> accounts);
	
	public void updateAccount(Account account);
	
	public BigDecimal getBalance(String accNumber);
	
	void transferFunds(TransactionService transactionService,
			AccountService accountService, Transaction senderTransaction,
			Transaction receiverTransaction, BigDecimal amount);
}
