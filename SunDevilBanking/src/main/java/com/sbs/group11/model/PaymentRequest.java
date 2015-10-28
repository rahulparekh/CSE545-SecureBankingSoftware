package com.sbs.group11.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * PaymentRequests - Tracks payment requests made by users and merchants. When a
 * payment has been initiated by the user and accepted and completed by the
 * merchant the request is accepted here and a new "pending" transaction is
 * added to the transactions table.
 */
@Entity
@Table(name = "PaymentRequest")
public class PaymentRequest {
	
	public PaymentRequest() {

    }
	
	public PaymentRequest(String merchantAccNumber, String customerAccNumber,
			String transactionID, int userAccepted, int merchantAccepted,
			BigDecimal amount, String type, String otp, int initiatedBy, 
			String customerName, String merchantName, String senderAccNumber, String receiverAccNumber) {
		super();
		this.merchantAccNumber = merchantAccNumber;
		this.customerAccNumber = customerAccNumber;
		this.transactionID = transactionID;
		this.userAccepted = userAccepted;
		this.merchantAccepted = merchantAccepted;
		this.amount = amount;
		this.type = type;
		this.otp = otp;
		this.OTPExpiry = new DateTime().toLocalDateTime().plusHours(2); // 2 hours expiry
		this.createdAt = new DateTime().toLocalDateTime();
		this.updatedAt = new DateTime().toLocalDateTime();
		this.initiatedBy = initiatedBy;
		this.customerName = customerName;
		this.merchantName = merchantName;
		this.senderAccNumber = senderAccNumber;
		this.receiverAccNumber = receiverAccNumber;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;	

	@NotNull
	@Size(min = 17, max = 17)
	@Column(name = "MerchantAccNumber", nullable = false, length = 17, unique = false)
	private String merchantAccNumber;
	
	@NotNull
	@Size(min = 17, max = 17)
	@Column(name = "CustomerAccNumber", nullable = false, length = 17, unique = false)
	private String customerAccNumber;
	
	@NotNull
	@Size(min = 17, max = 17)
	@Column(name = "SenderAccNumber", nullable = false, length = 17, unique = false)
	private String senderAccNumber;
	
	@NotNull
	@Size(min = 17, max = 17)
	@Column(name = "ReceiverAccNumber", nullable = false, length = 17, unique = false)
	private String receiverAccNumber;
	
	@NotNull
	@Size(min = 3, max = 70)
	@Column(name = "CustomerName", nullable = false, length = 70, unique = false)
	private String customerName;
	
	@NotNull
	@Size(min = 3, max = 70)
	@Column(name = "MerchantName", nullable = false, length = 70, unique = false)
	private String merchantName;
	
	@Size(min = 1, max = 17)
	@Column(name = "TransactionID", nullable = true, length = 17)
	private String transactionID;
	
	@NotNull
	@Digits(integer = 1, fraction = 0)
	@Column(name = "UserAccepted", nullable = false, columnDefinition = "int(1) DEFAULT '0'")
	private int userAccepted;
	
	@NotNull
	@Digits(integer = 1, fraction = 0)
	@Column(name = "MerchantAccepted", nullable = false, columnDefinition = "int(1) DEFAULT '0'")
	private int merchantAccepted;
	
	@NotNull
	@Digits(integer = 11, fraction = 2)
	@DecimalMin("0.01")
	@Column(name = "Amount", nullable = false)
	private BigDecimal amount;
	
	/** The type: Can be credit or debit */
	@NotEmpty
	@Size(min = 0, max = 10)
	@Column(name = "Type", nullable = false, length = 6)
	private String type;
	
	@NotEmpty
	@Size(min = 8, max = 8)
	@Column(name = "OTP", nullable = false, length = 8)
	private String otp;
	
	@NotNull
	@Column(name = "OTPExpiry", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime OTPExpiry;
	
	@NotNull
	@Column(name = "CreatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@NotNull
	@Column(name = "UpdatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime updatedAt;
	
	
	/** The initiated by: 0 for customer 1 for merchant */
	@NotNull
	@Digits(integer = 1, fraction = 0)
	@Column(name = "InitiatedBy", nullable = false, columnDefinition = "int(1) DEFAULT '0'")
	private int initiatedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMerchantAccNumber() {
		return merchantAccNumber;
	}

	public void setMerchantAccNumber(String merchantAccNumber) {
		this.merchantAccNumber = merchantAccNumber;
	}

	public String getCustomerAccNumber() {
		return customerAccNumber;
	}

	public void setCustomerAccNumber(String customerAccNumber) {
		this.customerAccNumber = customerAccNumber;
	}

	public String getSenderAccNumber() {
		return senderAccNumber;
	}

	public void setSenderAccNumber(String senderAccNumber) {
		this.senderAccNumber = senderAccNumber;
	}

	public String getReceiverAccNumber() {
		return receiverAccNumber;
	}

	public void setReceiverAccNumber(String receiverAccNumber) {
		this.receiverAccNumber = receiverAccNumber;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public int getUserAccepted() {
		return userAccepted;
	}

	public void setUserAccepted(int userAccepted) {
		this.userAccepted = userAccepted;
	}

	public int getMerchantAccepted() {
		return merchantAccepted;
	}

	public void setMerchantAccepted(int merchantAccepted) {
		this.merchantAccepted = merchantAccepted;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOTPExpiry() {
		return OTPExpiry;
	}

	public void setOTPExpiry(LocalDateTime oTPExpiry) {
		OTPExpiry = oTPExpiry;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(int initiatedBy) {
		this.initiatedBy = initiatedBy;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Override
	public String toString() {
		return "PaymentRequest [id=" + id + ", merchantAccNumber="
				+ merchantAccNumber + ", customerAccNumber="
				+ customerAccNumber + ", senderAccNumber=" + senderAccNumber
				+ ", receiverAccNumber=" + receiverAccNumber
				+ ", customerName=" + customerName + ", merchantName="
				+ merchantName + ", transactionID=" + transactionID
				+ ", userAccepted=" + userAccepted + ", merchantAccepted="
				+ merchantAccepted + ", amount=" + amount + ", type=" + type
				+ ", otp=" + otp + ", OTPExpiry=" + OTPExpiry + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", initiatedBy="
				+ initiatedBy + "]";
	}
	
}
