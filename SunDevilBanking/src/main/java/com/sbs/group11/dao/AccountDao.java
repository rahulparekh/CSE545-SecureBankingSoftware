package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.Account;

public interface AccountDao {
	
	List<Account> findByCustomerID(String CustomerID);
}
