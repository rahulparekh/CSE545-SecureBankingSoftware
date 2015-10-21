package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.TransactionDao;
import com.sbs.group11.model.Transaction;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {
	final static Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionDao dao;

	public void addTransaction(Transaction transaction) {
		dao.addTransaction(transaction);
	}

	public String getUniqueTransactionID() {
		// get 17 digit unique id
		String transactionID = UUID.randomUUID().toString().replace("-", "")
				.substring(0, 17);
		logger.debug("Generated transaction ID: " + transactionID);

		// check against the DB
		logger.info(dao.isUniqueTransactionID(transactionID));
		if (!dao.isUniqueTransactionID(transactionID)) {
			logger.debug("Not a unique transaction ID: " + transactionID);
			transactionID = getUniqueTransactionID();
		}

		logger.debug("Unique ID generated: " + transactionID);
		return transactionID;
	}

	public BigDecimal getBigDecimal(String number) {
		try {
			return new BigDecimal(number);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
