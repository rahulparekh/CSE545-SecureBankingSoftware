package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.AccountDao;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	final static Logger logger = Logger.getLogger(AccountServiceImpl.class);

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
	
	
	public BigDecimal getBalance(String accNumber){
		
		return dao.getBalance(accNumber);
	}

	public void updateAccount(Account account) {
		dao.updateAccount(account);		
	}
	

	public void transferFunds(TransactionService transactionService,
			AccountService accountService, Transaction senderTransaction,
			Transaction receiverTransaction, BigDecimal amount) {
		
		Account senderAccount = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());
		Account receiverAccount = accountService.getAccountByNumber(senderTransaction.getReceiverAccNumber());
		logger.debug("senderAccount: " + senderAccount);
		logger.debug("receiverAccount: " + receiverAccount);
		
		// update account balances
		senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
		
		logger.debug("senderAccount after updating balance: " + senderAccount);
		logger.debug("receiverAccount after updating balance: " + receiverAccount);
		
		// update transaction balances
		senderTransaction.setBalance(senderAccount.getBalance());
		receiverTransaction.setBalance(receiverAccount.getBalance());
		
		logger.debug("senderTransaction: " + senderTransaction);
		logger.debug("receiverTransaction: " + receiverTransaction);
		
		// create transactions
		transactionService.addTransaction(senderTransaction);
		transactionService.addTransaction(receiverTransaction);

	}

}
