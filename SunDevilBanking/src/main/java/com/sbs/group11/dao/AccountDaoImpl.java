package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Account;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements
		AccountDao {
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<Account> findByCustomerID(String customerID) {

		List<Account> accounts = new ArrayList<Account>();

		accounts = getSession()
					.createQuery("from Account where CustomerID=?")
					.setParameter(0, customerID)
					.list();

		return accounts;

	}
}
