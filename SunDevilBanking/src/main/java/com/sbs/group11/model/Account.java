package com.sbs.group11.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Account - Store external customer accounts.
 *
 * @author Rahul
 */

@Entity
@Table(name = "Account")
public class Account {
	
	/** The Customer ID. Foreign Key */
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "CustomerID", nullable = false, length = 11, unique = true)
	private String customerID;
	
	/** The number. */
	@Id
	@NotNull
	@Size(min = 17, max = 17)
	@Column(name = "Number", nullable = false, length = 17, unique = true)
	private String number;
	
	/** The name. */
	@NotNull
	@Size(min = 5, max = 20)
	@Column(name = "Name", nullable = false, length = 20)
	private String name;
	
	/** The type. */
	@NotNull
	@Digits(integer = 1, fraction = 0)
	@Column(name = "Type", nullable = false, columnDefinition = "int(1) DEFAULT '0'")
	private int type;
	
	/** The balance. */
	@NotNull
    @Digits(integer=11, fraction=2)
    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerID the new customer id
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(int type) {
		this.type = type;
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
	 * @param balance the new balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}	
	
}
