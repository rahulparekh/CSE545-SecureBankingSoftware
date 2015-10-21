package com.sbs.group11.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
	
	public Transaction(String transactionID, String receiverAccNumber,
			String senderAccNumber, String status, String type,
			BigDecimal amount) {
		super();
		this.transactionID = transactionID;
		this.receiverAccNumber = receiverAccNumber;
		this.senderAccNumber = senderAccNumber;
		this.status = status;
		this.type = type;
		this.amount = amount;
		this.createdAt = new DateTime().toLocalDateTime();
		this.updatedAt = new DateTime().toLocalDateTime();
	}


	/** The transaction id. */
	@Id
	@NotEmpty
	@Size(min = 17, max = 17)
	@Column(name = "TransactionID", nullable = false, length = 17, unique = true)
	private String transactionID;
	
	/** The receiver account number. */
	@NotEmpty
	@Size(min = 17, max = 17)
	@Column(name = "ReceiverAccNumber", nullable = false, length = 17)
	private String receiverAccNumber;
	
	/** The sender acc number. */
	@NotEmpty
	@Size(min = 17, max = 17)
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
    @Digits(integer=11, fraction=2)
	@DecimalMin("0.01")
    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;
	
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
	 * @param transactionID the new transaction id
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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
	 * @param receiverAccNumber the new receiver acc number
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
	 * @param senderAccNumber the new sender acc number
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
	 * @param status the new status
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
	 * @param type the new type
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
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @param createdAt the new created at
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
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID
				+ ", receiverAccNumber=" + receiverAccNumber
				+ ", senderAccNumber=" + senderAccNumber + ", status=" + status
				+ ", type=" + type + ", amount=" + amount + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	

}
