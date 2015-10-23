package com.sbs.group11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.AccountDao;
import com.sbs.group11.model.Account;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao dao;

	public List<Account> getAccountsByCustomerID(String customerID) {

		return dao.findByCustomerID(customerID);

	}

	public boolean isAccountNumberValid(String accNumber, List<Account> accounts) {
		if (accNumber != null && !accNumber.isEmpty()) {

			// See if the account submitted in the request
			// is belongs to the user. This will protect us against
			// any authenticated user request some other account number details
			for (Account account : accounts) {
				if (account.getNumber().equals(accNumber)) {
					return true;
				}
			}

		}

		return false;
	}

	public Account getAccountByNumber(String accNumber) {
		return dao.findByAccountNumber(accNumber);
	}
}
