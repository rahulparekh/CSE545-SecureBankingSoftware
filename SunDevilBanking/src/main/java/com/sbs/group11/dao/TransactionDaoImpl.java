package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;
import com.sbs.group11.service.AccountService;
import com.sbs.group11.service.TransactionService;
import com.sbs.group11.service.UserService;

@Repository("transactionDaoImpl")
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {
	
	@Autowired
    public AccountService accountService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public TransactionService transactionService;
	
	final static Logger logger = Logger.getLogger(TransactionDaoImpl.class);

	public void addTransaction(Transaction transaction) {
		transaction.setUpdatedAt(new DateTime().toLocalDateTime());
		getSession().saveOrUpdate(transaction);
	}


	public void updateTransaction(Transaction transaction) {
		transaction.setUpdatedAt(new DateTime().toLocalDateTime());
		getSession().merge(transaction);		
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
								+ "and MONTH(updatedAt) <= MONTH(CURRENT_DATE)")
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
						+ "and status = 'approved' "
						+ "and MONTHNAME(updatedAt) = :month "
						+ "and YEAR(updatedAt) = :year "
						+ "ORDER BY updatedAt ASC")
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
			getSession().evict(transactions.get(0));
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
		
       List<Transaction>  filteredTransaction = new ArrayList<Transaction>();
		
		for(int i=0; i < transactions.size();i++){
			
			Transaction transaction = transactions.get(i);
			Account account = accountService.getAccountByNumber(transaction.getSenderAccNumber());
			User user = userService.getUserbyCustomerID(account.getUser().getCustomerID());
			
			if(user.getEmployeeOverride()==1){
				
				filteredTransaction.add(transaction);
				
			}
		}
		
		if (filteredTransaction.size() > 0) {
				return filteredTransaction;
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
	   
	   
	   if (status.size() > 0 && 
			   status.get(0).getStatus().equalsIgnoreCase("pending")){			   
			   return true;
		} 	   
	   
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getPendingCriticalTransactions(){
		
		
		String pending_status="pending";
		//System.out.print("Inside gettransaction");
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where status = :pending_status Order By UpdatedAt ASC")
				.setParameter("pending_status", pending_status)
				.list();
		       
		
		if (transactions.size() > 0) {
				return transactions;
		} else {
				return null;
		}	
		
		
	}
	
	
	public void modifyTransaction(Transaction transaction){	
		transaction.setUpdatedAt(new DateTime().toLocalDateTime());
		getSession().update(transaction);				
	}
	

	public void  declineTransaction(Transaction transaction){
		transaction.setUpdatedAt(new DateTime().toLocalDateTime());
		transaction.setStatus("declined");
		getSession().update(transaction);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionsForAccountNumber(String accNumber){
		
		List<Transaction> transactions = new ArrayList<Transaction>();		
		transactions = getSession()
				.createQuery("from Transaction where senderAccNumber = :accNumber or receiverAccNumber = :accNumber")
				.setParameter("accNumber", accNumber)
				.list();
		
		
		
		
		if(transactions.size()>0)
			return transactions;
		
		return null;
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

