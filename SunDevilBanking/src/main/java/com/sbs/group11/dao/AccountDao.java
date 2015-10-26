package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.List;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;

public interface AccountDao {
	
	List<Account> findByCustomerID(String CustomerID);
	Account findByAccountNumber(String accNumber);
	public BigDecimal getBalance(String accNumber);
	public boolean creditorDebit(Transaction transaction);
}
