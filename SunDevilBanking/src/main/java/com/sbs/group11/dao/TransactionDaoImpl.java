package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;

@Repository("transactionDao")
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction>
		implements TransactionDao {

	final static Logger logger = Logger.getLogger(UserDaoImpl.class);

	public void addTransaction(Transaction transaction) {
		getSession().save(transaction);
	}

	@SuppressWarnings("unchecked")
	public boolean isUniqueTransactionID(String transactionID) {

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions = getSession()
				.createQuery(
						"from Transaction where TransactionID = :transactionID")
				.setParameter("transactionID", transactionID).list();
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
						"SELECT DISTINCT MONTHNAME(createdAt) AS Month, YEAR(createdAt) as Year "
						+ "from Transaction "
						+ "where "
						+ "(ReceiverAccNumber = :receiverAccNumber or SenderAccNumber = :senderAccNumber) "
						+ "and MONTH(createdAt) <= MONTH(CURRENT_DATE - INTERVAL 1 MONTH)")
				.setParameter("receiverAccNumber", accNumber)
				.setParameter("senderAccNumber", accNumber)
				.list();
		
		Iterator itr = result.iterator();
		while(itr.hasNext()){
			Object[] obj = (Object[]) itr.next();
			String month = String.valueOf(obj[0]);
			String year = String.valueOf(obj[1]);
			statements.add(new StatementMonthYear(month, year));
		}

		return statements;
	}

}
