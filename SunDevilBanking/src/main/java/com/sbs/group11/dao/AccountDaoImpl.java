package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Account;
import com.sbs.group11.service.TransactionService;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements
		AccountDao {
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	@Autowired
	TransactionService transactionService;

	@SuppressWarnings("unchecked")
	public List<Account> findByCustomerID(String customerID) {

		List<Account> accounts = new ArrayList<Account>();

		accounts = getSession()
					.createQuery("from Account where CustomerID=?")
					.setParameter(0, customerID)
					.list();

		return accounts;

	}

	public Account findByAccountNumber(String accNumber) {
		
		Account account = new Account();

		account = (Account) getSession().get(Account.class, accNumber);
		
		if (account != null)
			return account;
		else
			return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal getBalance(String accNumber){
		
		
		List<Account> accountlist = getSession()
				.createQuery("from Account where Number = :accNumber")
				.setParameter("accNumber", accNumber).list();
		
		Account account = null;
		
		if (accountlist.size() > 0) {
			account = accountlist.get(0);
		} else {
			return null;
		}		
			
		return account.getBalance();
	}
	
	public void updateAccount(Account account){
		getSession().update(account);
	}
	
}
