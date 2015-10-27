package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;

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
	public boolean creditorDebit(Transaction transaction){
		
		if( transaction == null ) 
			return false;
				
		
		Account senderAcc = findByAccountNumber(transaction.getSenderAccNumber());
		Account recieverAcc = findByAccountNumber(transaction.getReceiverAccNumber());
		BigDecimal amount = transaction.getAmount();
		BigDecimal zero = BigDecimal.ZERO;
		LocalDateTime now = LocalDateTime.now();
        
		logger.debug("amount.compare: "+ amount.compareTo(zero) + " amount:balance " + amount.compareTo(senderAcc.getBalance()));
		
		if(amount.compareTo(zero) == 1 && senderAcc.getBalance().compareTo(amount) == 1){
			logger.debug("Inside Account updation");
			senderAcc.setBalance(senderAcc.getBalance().subtract(amount));
			senderAcc.setUpdatedAt(now);
			recieverAcc.setBalance(recieverAcc.getBalance().add(amount));
			recieverAcc.setUpdatedAt(now);
			getSession().update(senderAcc);
			getSession().update(recieverAcc);
			return true;
			
		}	
			
		return false;

		
	}
}
