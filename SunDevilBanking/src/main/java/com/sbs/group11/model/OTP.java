package com.sbs.group11.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "Otp")
public class OTP {
	
	public OTP() {
		
	}
	
	public OTP(String otp, String customerID,
			String type) {
		super();
		this.otp = otp;
		this.OTPExpiry = new DateTime().toLocalDateTime().plusMinutes(10);
		this.customerID = customerID;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", 
		unique = true, nullable = false)
	private int id;
	
	@NotEmpty
	@Size(min = 60, max = 60)
	@Column(name = "OTP", nullable = true, length = 60)
	private String otp;
	
	@NotNull
	@Column(name = "OTPExpiry", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime OTPExpiry;	
	
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "CustomerID", nullable = false, length = 11, unique = false)
	private String customerID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TransactionID", nullable = false)
	private Transaction transaction;
	
	@NotEmpty
	@Size(min = 0, max = 20)
	@Column(name = "Type", nullable = false, length = 20, unique = false)
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OTP [id=" + id + ", otp=" + otp + ", OTPExpiry=" + OTPExpiry
				+ ", customerID=" + customerID + ", type=" + type + "]";
	}
	
	
}
