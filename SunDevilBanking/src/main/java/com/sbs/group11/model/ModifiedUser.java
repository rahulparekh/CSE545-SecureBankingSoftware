package com.sbs.group11.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * User - Hibernate model for Users with user type and roles. One model for all
 * users is easier to work with and much more efficient than having different
 * tables for external and internal users. This will integrate much better with
 * spring security too and provide a more solid and consistent authentication
 * framework.
 *
 * @author Rahul
 */

@Entity
@Table(name = "ModifiedUser")
public class ModifiedUser {

	/**
	 * The customer id. No auto increment as revealing how many users we have in
	 * the bank might be insecure. We should have it randomized.
	 */
	
	
	
	@Id
	@Column(name = "RequestId")
	private long  requestid;
	
	
	
	
	public long getRequestid() {
		return requestid;
	}

	public void setRequestid(long requestid) {
		this.requestid = requestid;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "CustomerID", nullable = false, length = 11)
	private String customerID;

	public ModifiedUser() {
	
		customerID = "";
	
	}

	public ModifiedUser(String customerID, Set<SecurityQuestion> securityQuestions,
			Set<Account> accounts) {
		this.customerID = customerID;
	}

	/** The first name. */
	@NotNull
	@Size(min = 2, max = 35)
	@Column(name = "FirstName", nullable = false, length = 35)
	private String firstName;

	/** The middle name. */
	@Size(min = 3, max = 35)
	@Column(name = "MiddleName", nullable = true, length = 35)
	private String middleName;

	/** The last name. */
	@NotNull
	@Size(min = 3, max = 70)
	@Column(name = "LastName", nullable = false, length = 70)
	private String lastName;

	/** The address line1. */
	@NotNull
	@Size(min = 5, max = 50)
	@Column(name = "AddressLine1", nullable = false, length = 50)
	private String addressLine1;

	/** The address line2. */
	@Size(min = 5, max = 50)
	@Column(name = "AddressLine2", nullable = true, length = 50)
	private String addressLine2;

	/** The state. */
	@NotNull
	@Size(min = 2, max = 2)
	@Column(name = "State", nullable = false, length = 2)
	private String state;

	/** The zip code. */
	@NotNull
	@Size(min = 5, max = 5)
	@Column(name = "ZipCode", nullable = false, length = 5)
	private String zipCode;

	/**
	 * The phone No. 15 characters and string as we might have to take
	 * international codes into account
	 */
	@NotNull
	@Size(min = 10, max = 15)
	@Column(name = "Phone", nullable = false, length = 15)
	private String phone;

	/** The email. */
	@NotNull
	@Size(max = 255)
	@Column(name = "Email", nullable = false, length = 255)
	private String email;

	/** The password. 60 characters as we will use BCrypt hash */
	@NotNull
	@Size(min = 6, max = 60)
	@Column(name = "Password", nullable = false, length = 60)
	private String password;

	/** The employee override. */
	@Digits(integer = 1, fraction = 0)
	@Column(name = "EmployeeOverride", nullable = true, columnDefinition = "int(1) DEFAULT '0'")
	private int employeeOverride;
	
	/** If the user is enabled. */
	@Digits(integer = 1, fraction = 0)
	@Column(name = "Enabled", nullable = true, columnDefinition = "int(1) DEFAULT '0'")
	private int enabled;

	/** The customer type. Customer, Merchant, Employee, Manager, Admin */
	@Size(min = 5, max = 8)
	@Column(name = "UserType", nullable = true, length = 8)
	private String userType;

	/** The created at. */
	@NotNull
	@Column(name = "CreatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	/** The last login at. */
	@NotNull
	@Column(name = "LastLoginAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime lastLoginAt;

	/** The updated at. */
	@NotNull
	@Column(name = "UpdatedAt", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime updatedAt;
	
	
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name = "Status", nullable = false, length = 10)
	private String Status;


	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName
	 *            the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the address line1.
	 *
	 * @return the address line1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Sets the address line1.
	 *
	 * @param addressLine1
	 *            the address line 1
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Gets the address line2.
	 *
	 * @return the address line2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Sets the address line2.
	 *
	 * @param addressLine2
	 *            the new address line2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode
	 *            the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

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
	 * @param customerID
	 *            the new customer id
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
		

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the employee override.
	 *
	 * @return the employee override
	 */
	public int getEmployeeOverride() {
		return employeeOverride;
	}

	/**
	 * Sets the employee override.
	 *
	 * @param employeeOverride
	 *            the new employee override
	 */
	public void setEmployeeOverride(int employeeOverride) {
		this.employeeOverride = employeeOverride;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
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
	 * Gets the last login at.
	 *
	 * @return the last login at
	 */
	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	/**
	 * Sets the last login at.
	 *
	 * @param lastLoginAt
	 *            the new last login at
	 */
	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
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

}
