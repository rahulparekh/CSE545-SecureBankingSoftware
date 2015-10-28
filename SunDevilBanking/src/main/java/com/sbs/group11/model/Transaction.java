package com.sbs.group11.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Transaction - Model for transactions.
 * 
 * @author Rahul
 */
@Entity
@Table(name = "Transaction")
public class Transaction {

	public Transaction() {

	}

	public Transaction(String transactionID, String name,
			String receiverAccNumber, String senderAccNumber, String status,
			String type, BigDecimal amount, String isCritical,
			String transactionOwner, String pairId) {
		super();
		this.transactionID = transactionID;
		this.name = name;
		this.receiverAccNumber = receiverAccNumber;
		this.senderAccNumber = senderAccNumber;
		this.status = status;
		this.type = type;
		this.amount = amount;
		this.createdAt = new DateTime().toLocalDateTime();
		this.updatedAt = new DateTime().toLocalDateTime();

		this.isCritical = isCritical;
		this.transactionOwner = transactionOwner;
		this.pairId = pairId;
	}

	public Transaction(String transactionID, String name,
			String receiverAccNumber, String senderAccNumber, String status,
			String type, BigDecimal amount, BigDecimal balance,
			String isCritical, String transactionOwner, String pairId) {
		super();
		this.transactionID = transactionID;
		this.name = name;
		this.receiverAccNumber = receiverAccNumber;
		this.senderAccNumber = senderAccNumber;
		this.status = status;
		this.type = type;
		this.amount = amount;
		this.createdAt = new DateTime().toLocalDateTime();
		this.updatedAt = new DateTime().toLocalDateTime();
		this.balance = balance;
		this.isCritical = isCritical;
		this.transactionOwner = transactionOwner;
		this.pairId = pairId;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "transaction")
	private Set<OTP> otp = new HashSet<OTP>(0);

	public Set<OTP> getOtp() {
		return otp;
	}

	public void setOtp(Set<OTP> otp) {
		this.otp = otp;
	}

	/** Payment Requests. For the One-to-Many relationship */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "TransactionID")
	private Set<PaymentRequest> paymentRequests = new HashSet<PaymentRequest>(0);

	/** The transaction id. */
	@Id
	@NotEmpty
	@Size(min = 1, max = 17)
	@Column(name = "TransactionID", nullable = false, length = 17, unique = true)
	private String transactionID;

	/** The transaction name (description). */
	@NotEmpty
	@Size(min = 3, max = 255)
	@Column(name = "Name", nullable = false, length = 255, unique = false)
	private String name;

	/** The receiver account number. */
	@NotEmpty
	@Size(min = 1, max = 17)
	@Column(name = "ReceiverAccNumber", nullable = false, length = 17)
	private String receiverAccNumber;

	/** The sender acc number. */
	@NotEmpty
	@Size(min = 1, max = 17)
	@Column(name = "SenderAccNumber", nullable = false, length = 17)
	private String senderAccNumber;

	/** The status. */
	@NotEmpty
	@Size(min = 0, max = 10)
	@Column(name = "Status", nullable = false, length = 10)
	private String status;

	/** The type: Can be credit or debit */
	@NotEmpty
	@Size(min = 0, max = 10)
	@Column(name = "Type", nullable = false, length = 6)
	private String type;

	/** The amount. */
	@NotNull
	@Digits(integer = 11, fraction = 2)
	@DecimalMin("0.01")
	@Column(name = "Amount", nullable = false)
	private BigDecimal amount;

	/** The amount. */
	@Digits(integer = 11, fraction = 2)
	@Column(name = "Balance", nullable = true)
	private BigDecimal balance;

	/** The transaction owner */

	@Size(min = 0, max = 17)
	@Column(name = "TransactionOwner", nullable = false, length = 17)
	private String transactionOwner;

	@NotEmpty
	@Size(min = 0, max = 6)
	@Column(name = "isCritical", nullable = false, length = 6)
	private String isCritical;

	public String getIsCritical() {
		return isCritical;
	}

	public void setIsCritical(String isCritical) {
		this.isCritical = isCritical;
	}

	/** The created at. */
	@NotNull
	@Column(name = "CreatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	/** The updated at. */
	@NotNull
	@Column(name = "UpdatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime updatedAt;

	/**
	 * The pair id. To maintain a single action which leads to two transctions.
	 * i.e. payments and fund transfer
	 */
	@Column(name = "PairId", nullable=true, length=36)
	private String pairId;

	/**
	 * The month. This is not to be part of the database table as we can easily
	 * query it with MySQLs "monthname"
	 */
	@Transient
	private String month;

	public Set<PaymentRequest> getPaymentRequests() {
		return paymentRequests;
	}

	public void setPaymentRequests(Set<PaymentRequest> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionID
	 *            the new transaction id
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the receiver acc number.
	 *
	 * @return the receiver acc number
	 */
	public String getReceiverAccNumber() {
		return receiverAccNumber;
	}

	/**
	 * Sets the receiver acc number.
	 *
	 * @param receiverAccNumber
	 *            the new receiver acc number
	 */
	public void setReceiverAccNumber(String receiverAccNumber) {
		this.receiverAccNumber = receiverAccNumber;
	}

	/**
	 * Gets the sender acc number.
	 *
	 * @return the sender acc number
	 */
	public String getSenderAccNumber() {
		return senderAccNumber;
	}

	/**
	 * Sets the sender acc number.
	 *
	 * @param senderAccNumber
	 *            the new sender acc number
	 */
	public void setSenderAccNumber(String senderAccNumber) {
		this.senderAccNumber = senderAccNumber;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Sets the balance.
	 *
	 * @param balance
	 *            the new balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Gets the transaction owner.
	 *
	 * @return the transaction owner
	 */
	public String getTransactionOwner() {
		return transactionOwner;
	}

	/**
	 * Sets the transaction owner.
	 *
	 * @param transactionOwner
	 *            the new transaction owner
	 */
	public void setTransactionOwner(String transactionOwner) {
		this.transactionOwner = transactionOwner;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt
	 *            the new created at
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt
	 *            the new updated at
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	@Override
	public String toString() {
		return "Transaction [paymentRequests=" + paymentRequests
				+ ", transactionID=" + transactionID + ", name=" + name
				+ ", receiverAccNumber=" + receiverAccNumber
				+ ", senderAccNumber=" + senderAccNumber + ", status=" + status
				+ ", type=" + type + ", amount=" + amount + ", balance="
				+ balance + ", transactionOwner=" + transactionOwner
				+ ", isCritical=" + isCritical + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", pairId=" + pairId
				+ ", month=" + month + "]";
	}

}
