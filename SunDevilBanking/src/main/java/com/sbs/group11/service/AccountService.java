package com.sbs.group11.service;

import java.util.List;

import com.sbs.group11.model.Account;

public interface AccountService {
	List<Account> getAccountsByCustomerID(String customerID);
	Account getAccountByNumber(String accNumber);
	boolean isAccountNumberValid(String accNumber, List<Account> accounts);
}
