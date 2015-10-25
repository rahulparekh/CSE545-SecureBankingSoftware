package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.AccountDao;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao dao;

	public List<Account> getAccountsByCustomerID(String customerID) {

		return dao.findByCustomerID(customerID);

	}

	public Account getValidAccountByNumber(String accNumber,
			List<Account> accounts) {
		if (accNumber != null && !accNumber.isEmpty()) {

			// See if the account submitted in the request
			// is belongs to the user. This will protect us against
			// any authenticated user request some other account number details
			for (Account account : accounts) {
				if (account.getNumber().equals(accNumber)) {
					return account;
				}
			}

		}

		return null;
	}

	public Account getAccountByNumber(String accNumber) {
		return dao.findByAccountNumber(accNumber);
	}

	public void transferFunds(TransactionService transactionService,
			AccountService accountService, Transaction senderTransaction,
			Transaction receiverTransaction, BigDecimal amount) {
		
		Account senderAccount = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());
		Account receiverAccount = accountService.getAccountByNumber(receiverTransaction.getSenderAccNumber());
		
		// update account balances
		senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
		
		// update transaction balances
		senderTransaction.setBalance(receiverAccount.getBalance());
		receiverTransaction.setBalance(receiverAccount.getBalance());
		
		// create transactions
		transactionService.addTransaction(senderTransaction);
		transactionService.addTransaction(receiverTransaction);

	}

}
