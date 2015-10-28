package com.sbs.group11.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.OTP;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.service.AccountService;

@Repository("transactionDaoImpl")
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {
	
	@Autowired
    public AccountService accountService;
	
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);

	public void addTransaction(Transaction transaction) {
		
		getSession().saveOrUpdate(transaction);
	}

	@SuppressWarnings("unchecked")
	public boolean isUniqueTransactionID(String transactionID) {

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

	@SuppressWarnings("unchecked")
	public List<StatementMonthYear> getStatementMonths(String accNumber) {

		List<StatementMonthYear> statements = new ArrayList<StatementMonthYear>();
		List<Object> result = (List<Object>) getSession()
				.createSQLQuery(
						"SELECT DISTINCT MONTHNAME(updatedAt) AS Month, YEAR(updatedAt) as Year "
								+ "from Transaction "
								+ "where "
								+ "TransactionOwner = :accNumber "
								+ "and MONTH(updatedAt) <= MONTH(CURRENT_DATE - INTERVAL 1 MONTH)")
				.setParameter("accNumber", accNumber).list();

		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			String month = String.valueOf(obj[0]);
			String year = String.valueOf(obj[1]);
			statements.add(new StatementMonthYear(month, year));
		}

		return statements;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getCompletedTransactionsByAccountNummber(String accNumber, String month, int year) {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = getSession()
				.createQuery(
						"from Transaction "
						+ "where "
						+ "TransactionOwner = :accNumber "
						+ "and status = 'completed' "
						+ "and MONTHNAME(updatedAt) = :month "
						+ "and YEAR(updatedAt) = :year "
						+ "ORDER BY UpdatedAt ASC")
				.setParameter("accNumber", accNumber)
				.setParameter("month", month)
				.setParameter("year", year)
				.list();
		
		return transactions;

	}

	
	@SuppressWarnings("unchecked")
	public Transaction getTransactionByID(String transactionID){
		
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
		
		String isCriticalValue = "No";
		String pending_status="pending";
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where status = :pending_status and isCritical = :isCriticalValue")
				.setParameter("pending_status", pending_status).setParameter("isCriticalValue",isCriticalValue)
				.list();
		if (transactions.size() > 0) {
			return transactions;
		} else {
			return null;
		}		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean isTransactionPending(String transactionID){
		
	   
	   List<Transaction> status = getSession().
			         createQuery("from Transaction where TransactionID = :transactionID")
				     .setParameter("transactionID", transactionID)
				     .list();
	   
	   if (status.size() > 0) {
			
		   if(status.get(0).getStatus().equals("pending")){
			   
			   
			   
			   return true;
			   
		   }
		   else{
			   return false;
		   }
		   
		} else {
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getPendingCriticalTransactions(){
		
		String isCriticalValue = "Yes";
		String pending_status="pending";
		System.out.print("Inside gettransaction");
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where status = :pending_status and isCritical = :isCriticalValue")
				.setParameter("pending_status", pending_status).setParameter("isCriticalValue",isCriticalValue)
				.list();
		if (transactions.size() > 0) {
			return transactions;
		} else {
			return null;
		}		
	}
	
	
    public boolean approveTransaction(Transaction transaction){
    	
		  
    	
    	if(!isTransactionPending(transaction.getTransactionID())) return false; 
    	
		    boolean result = accountService.creditorDebit(transaction);
		    if(result){
		    	transaction.setStatus("approved");
		    	 getSession().update(transaction);
		    	 return result;
		    	 
		    }
		    else{
		    	transaction.setStatus("pending");
		    	getSession().update(transaction);
		    	return result;
		    }
    	
    }
	
	
	public void modifyTransaction(Transaction transaction){
		
		getSession().update(transaction);
		String senderAccNumber= transaction.getSenderAccNumber();
		String recieverAccNumber = transaction.getReceiverAccNumber();
		// TODO: Use accounts credit debit service to submit the transaction
		
		
	}
	

	public void  declineTransaction(Transaction transaction){
		
		transaction.setStatus("declined");
		getSession().update(transaction);
	}

	public Transaction getTransactionByPairId(String pairId,
			String transactionID) {
		
		Transaction transaction = (Transaction) getSession()
				.createQuery("from Transaction where pairId = :pairId and TransactionID <> :transactionID")
				.setParameter("pairId", pairId)
				.setParameter("transactionID", transactionID)
				.setMaxResults(1)
				.uniqueResult();
		
		return transaction;
	}

}
