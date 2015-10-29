package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.List;

import com.sbs.group11.model.Account;

public interface AccountDao {
	
	List<Account> findByCustomerID(String CustomerID);
	Account findByAccountNumber(String accNumber);
	public BigDecimal getBalance(String accNumber);
	public void updateAccount(Account account);
}
