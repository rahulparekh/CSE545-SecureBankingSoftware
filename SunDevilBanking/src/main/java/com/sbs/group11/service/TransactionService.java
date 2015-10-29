package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.PaymentRequest;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

/**
 * The Interface TransactionService.
 */
public interface TransactionService {

	/**
	 * Adds the transaction.
	 *
	 * @param transaction
	 *            the transaction
	 */
	void addTransaction(Transaction transaction);
	
	void updateTransaction(Transaction transaction);

	/**
	 * Gets the unique transaction id.
	 *
	 * @return the unique transaction id
	 */
	String getUniqueTransactionID();

	/**
	 * Gets the big decimal.
	 *
	 * @param accNumber
	 *            the acc number
	 * @return the big decimal
	 */
	BigDecimal getBigDecimal(String accNumber);

	/**
	 * Gets the statement months.
	 *
	 * @param accNumber
	 *            the acc number
	 * @return the statement months
	 */
	List<StatementMonthYear> getStatementMonths(String accNumber);

	/**
	 * Gets the completed transactions by account nummber.
	 *
	 * @param accNumber
	 *            the acc number
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @return the completed transactions by account nummber
	 */
	List<Transaction> getCompletedTransactionsByAccountNummber(
			String accNumber, String month, int year);
	
	String isCritical(BigDecimal amount, BigDecimal critical_value);

	/**
	 * Gets the transaction.
	 *
	 * @param transactionID
	 *            the transaction id
	 * @return the transaction
	 */
	Transaction getTransaction(String transactionID);

	/**
	 * Gets the pending transactions.
	 *
	 * @return the pending transactions
	 */
	List<Transaction> getPendingTransactions();

	/**
	 * Gets the pending critical transaction.
	 *
	 * @return the pending critical transaction
	 */
	public List<Transaction> getPendingCriticalTransaction();

	
	boolean approveTransactionafterModification(Transaction transaction);

	boolean approveTransaction(Transaction transaction);

	/**
	 * Decline transaction.
	 *
	 * @param transactionID
	 *            the transaction id
	 */
	void declineTransaction(String transactionID);

	/**
	 * Modify transaction.
	 *
	 * @param transaction
	 *            the transaction
	 */
	void modifyTransaction(Transaction transaction);

	/**
	 * Checks if is transfer account valid.
	 *
	 * @param accountService
	 *            the account service
	 * @param transactionService
	 *            the transaction service
	 * @param senderAccounts
	 *            the sender accounts
	 * @param request
	 *            the request
	 * @param model
	 *            the model
	 * @param user
	 *            the user
	 * @param attr
	 *            the attr
	 * @return true, if is transfer account valid
	 */
	boolean isTransferAccountValid(List<Account> senderAccounts, HttpServletRequest request,
			ModelMap model, User user, RedirectAttributes attr);
	
	List<Transaction> getTransactionsForAccountNumber(String accNumber);

	/**
	 * Initiate payment. Creates the payment request and notifies other users about it.
	 *
	 * @param paymentRequest the payment request
	 */
	void initiatePayment(PaymentRequest paymentRequest);

	/**
	 * Accept payment i.e. the user/merchant accepts the payment request made by
	 * the other
	 *
	 * @param paymentRequest the payment request
	 */
	void acceptPayment(PaymentRequest paymentRequest);

	
	/**
	 * Gets the payments by acc number.
	 *
	 * @param accNumber the acc number
	 * @param initiatedBy the initiated by
	 * @return the payments by acc number
	 */
	List<PaymentRequest> getPaymentsByAccNumber(String accNumber, int initiatedBy);
	
	/**
	 * Gets the payment request.
	 *
	 * @param id the id
	 * @return the payment request
	 */
	PaymentRequest getPaymentRequest(int id);
	
	
	/**
	 * Gets the transaction by pair id and excludes provided transactionID
	 *
	 * @param pairId the pair id
	 * @param transactionID the transaction id
	 * @return the transaction by pair id
	 */
	Transaction getTransactionByPairId(String pairId, String transactionID);

	boolean updateTransactionPair(Transaction transaction, String string, BigDecimal balance);

}
