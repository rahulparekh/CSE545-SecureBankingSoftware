package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;
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
	
	
//	public boolean creditOrDebit(Transaction transaction){
//		
//		if( transaction == null ) 
//			return false;
//				
//		
//		Account senderAcc = findByAccountNumber(transaction.getSenderAccNumber());
//		Account recieverAcc = findByAccountNumber(transaction.getReceiverAccNumber());
//		BigDecimal amount = transaction.getAmount();
//		BigDecimal zero = BigDecimal.ZERO;
//		LocalDateTime now = LocalDateTime.now();
//        
//		logger.debug("amount.compare: "+ amount.compareTo(zero) + " amount:balance " + amount.compareTo(senderAcc.getBalance()));
//		
//		// For other transactions
//		if(amount.compareTo(zero) == 1 && senderAcc.getBalance().compareTo(amount) == 1){
//			
//			// Means credit debit type of transactions
//			if (senderAcc.getNumber().equals(recieverAcc.getNumber())) {
//				if(transaction.getType().equalsIgnoreCase("credit")) {
//					senderAcc.setBalance(senderAcc.getBalance().add(amount));
//				}
//				
//				// otherwise debit
//				senderAcc.setBalance(senderAcc.getBalance().subtract(amount));				
//				transaction.setBalance(senderAcc.getBalance());
//				transactionService.updateTransaction(transaction);
//				
//			} else {
//				
//				logger.debug("Inside Account updation");
//				senderAcc.setBalance(senderAcc.getBalance().subtract(amount));
//				senderAcc.setUpdatedAt(now);
//				recieverAcc.setBalance(recieverAcc.getBalance().add(amount));
//				recieverAcc.setUpdatedAt(now);
//				transaction.setBalance(senderAcc.getBalance());
//				transactionService.updateTransaction(transaction);
//				transaction.setBalance(recieverAcc.getBalance());
//				transactionService.updateTransaction(transaction);				
//				getSession().update(senderAcc);
//				getSession().update(recieverAcc);
//				
//			}
//			
//			return true;
//			
//		}	
//			
//		return false;
//
//		
//	}
}
