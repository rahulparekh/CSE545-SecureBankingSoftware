package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

@Repository("transactionDaoImpl")
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {
	
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public void addTransaction(Transaction transaction) {		
		getSession().save(transaction);
	}
	
	@SuppressWarnings("unchecked")
	public boolean isUniqueTransactionID(String transactionID){
		
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from transaction where TransactionID = :transactionID")
				.setParameter("transactionID", transactionID)
				.list();
		if (transactions.isEmpty()) {
			return true;
		}
		
		return false;		
	}

	
	@SuppressWarnings("unchecked")
	public Transaction getTransactionByID(String transactionID){
		
		System.out.print("Inside gettransaction");
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where TransactionID = :transactionID")
				.setParameter("transactionID", transactionID)
				.list();
		if (transactions.size() > 0) {
			return transactions.get(0);
		} else {
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getPendingTransactions(){
		
		String pending_status="2";
		System.out.print("Inside gettransaction");
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where status = :pending_status")
				.setParameter("pending_status", pending_status)
				.list();
		if (transactions.size() > 0) {
			return transactions;
		} else {
			return null;
		}		
	}
	
	
    public void approveTransaction(Transaction transaction){
    	
		    getSession().update(transaction);
    	
    	
    }
	
	
	public void modifyTransaction(Transaction transaction){
		
		getSession().update(transaction);
		String senderAccNumber= transaction.getSenderAccNumber();
		String recieverAccNumber = transaction.getReceiverAccNumber();
		// TODO: Use accounts credit debit service to submit the transaction
		
		
	}
	

	public void  deleteTransaction(Transaction transaction){
		
		getSession().delete(transaction);
	}

}
