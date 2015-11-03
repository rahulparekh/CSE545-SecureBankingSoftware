package com.sbs.group11.model;

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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "User")
public class User {

	/** The security questions. For the One-to-Many relationship */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<SecurityQuestion> securityQuestions = new HashSet<SecurityQuestion>(
			0);

	/** The accounts. For the One-to-Many relationship */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<Account> accounts = new HashSet<Account>(0);

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<Role> role = new HashSet<Role>(0);

	/**
	 * The customer id. No auto increment as revealing how many users we have in
	 * the bank might be insecure. We should have it randomized.
	 */
	@Id
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "CustomerID", nullable = false, length = 11, unique = true)
	private String customerID;

	public User() {
	
		customerID = "";
	
	}

	public User(String customerID, Set<SecurityQuestion> securityQuestions,
			Set<Account> accounts) {
		this.customerID = customerID;
		this.securityQuestions = securityQuestions;
		this.accounts = accounts;
	}

	/** The first name. */
	@NotNull
	@Size(min = 2, max = 35)
	@Column(name = "FirstName", nullable = false, length = 35)
	private String firstName;

	/** The middle name. */
	@Size(min = 0, max = 35)
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
	
	@Column(name = "publicKey")
	@Type(type="text")
	//@Column(name = "publicKey", nullable = true, length = 5000 )
	private String publicKey;

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
	@Column(name = "Email", nullable = false, length = 255, unique = true)
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
	
	
	@Digits(integer = 1, fraction = 0)
	@Column(name = "PII", nullable = true, columnDefinition = "int(1) DEFAULT '0'")
	private int PII;

	public int getPII() {
		return PII;
	}

	public void setPII(int pII) {
		PII = pII;
	}

	/**
	 * Gets the security questions. Defines a 1 to Many relationship
	 *
	 * @return the security questions
	 */
	public Set<SecurityQuestion> getSecurityQuestions() {
		return this.securityQuestions;
	}

	/**
	 * Sets the security questions.
	 *
	 * @param securityQuestions
	 *            the new security questions
	 */
	public void setSecurityQuestions(Set<SecurityQuestion> securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	/**
	 * Sets the accounts.
	 *
	 * @param accounts
	 *            the new accounts
	 */
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
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
	 * Sets the public key
	 *
	 * @param public key
	 *            the new public key
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
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
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getUserType() {
		return userType;
	}
	
	/**
	 * Gets the public key
	 *
	 * @return the public key
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param userType
	 *            the new customer type
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Set<Role> getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role
	 *            the new role
	 */
	public void setRole(Set<Role> role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "User [accounts="
				+ accounts + ", role=" + role + ", customerID=" + customerID
				+ ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", state=" + state
				+ ", zipCode=" + zipCode + ", phone=" + phone + ", email="
				+ email + ", password=" + password + ", employeeOverride="
				+ employeeOverride + ", enabled=" + enabled + ", userType="
				+ userType + ", createdAt=" + createdAt + ", lastLoginAt="
				+ lastLoginAt + ", updatedAt=" + updatedAt + "]";
	}

}
