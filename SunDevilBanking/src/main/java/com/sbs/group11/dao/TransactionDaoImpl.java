package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Transaction;

@Repository("transactionDao")
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {
	
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public void addTransaction(Transaction transaction) {		
		getSession().save(transaction);
	}
	
	@SuppressWarnings("unchecked")
	public boolean isUniqueTransactionID(String transactionID){
		
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where TransactionID = :transactionID")
				.setParameter("transactionID", transactionID)
				.list();
		if (transactions.isEmpty()) {
			return true;
		}
		
		return false;		
	}

}
