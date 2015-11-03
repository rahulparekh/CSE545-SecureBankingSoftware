package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sbs.group11.dao.InternalUserDaoImpl;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.Role;
import com.sbs.group11.model.SecurityQuestion;
import com.sbs.group11.model.User;

/*
 * InternalUserServiceImpl: Used to implement various methods which internal
 * users perform. Do not use this for general user methods like collecting the
 * user details or saving the user as those will be performed the user service.
 */
@Component
@Service("InternalUserServiceImpl")
@Transactional
public class InternalUserServiceImpl implements InternalUserService {

	final static Logger logger = Logger
			.getLogger(InternalUserServiceImpl.class);

	@Autowired
	private InternalUserDaoImpl dao;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptHashService hashService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PkiService pkiService;

	public void addInternalUser(User user) {

		
		String customerID;
		// Logic here to add a user
		while (true) {
			customerID = "" + generateRandomNumberOfLength(11);
			if (findUserByID(customerID) == null)
				;
			{
				break;
			}
		}
		Set<SecurityQuestion> secquestions = user.getSecurityQuestions();
		for (SecurityQuestion question : secquestions) {

			question.setUser(user);

		}
		user.setCustomerID(customerID);
		user.setCreatedAt(LocalDateTime.now());
		user.setLastLoginAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setEnabled(1); // enable the user

		// hash password
		String password = hashService.getBCryptHash(user.getPassword());
		user.setPassword(password);

		// set role or roles
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setUser(user);
		role.setRole("ROLE_" + user.getUserType().toUpperCase());
		roles.add(role);
		user.setRole(roles);

		if (user.getUserType().toString().equalsIgnoreCase("merchant")
				|| user.getUserType().toString().equalsIgnoreCase("customer")) {

			Set<Account> accounts = new HashSet<Account>();
			Account account1 = new Account();
			Account account2 = new Account();

			String checking_account;
			while (true) {
				checking_account = "" + generateRandomNumberOfLength(17);
				if (accountService.getAccountByNumber(checking_account) == null)
					;
				{
					break;
				}
			}

			String saving_account;
			while (true) {
				saving_account = "" + generateRandomNumberOfLength(17);
				if (accountService.getAccountByNumber(saving_account) == null)
					;
				{
					break;
				}
			}

			account1.setNumber(checking_account);
			account1.setBalance(new BigDecimal(0.0));
			account1.setCreatedAt(LocalDateTime.now());
			account1.setUser(user);
			account1.setName(user.getFirstName() + " checking account");
			account1.setType(0);
			account1.setUpdatedAt(LocalDateTime.now());

			account2.setNumber(saving_account);
			account2.setBalance(new BigDecimal(0.0));
			account2.setCreatedAt(LocalDateTime.now());
			account2.setUser(user);
			account2.setName(user.getFirstName() + " saving account");
			account2.setType(1);
			account2.setUpdatedAt(LocalDateTime.now());

			accounts.add(account1);
			accounts.add(account2);

			user.setAccounts(accounts);

			String publickey = pkiService.generatekeypair(user.getEmail());
			user.setPublicKey(publickey);

		}
		
		logger.info(user);

		dao.saveInternalUser(user);
	}

	public void updateInternalUser(User user) {
		// Logic here to add a user

		dao.updateInternalUser(user);
	}

	public User searchInternalUser(String EmployeeID) {
		return dao.findInternalUserByID(EmployeeID);

	}

	public User searchInternalUserByType(String userType) {
		return dao.findUserByType(userType);

	}

	public void deleteInternalUserById(String id) {
		dao.deleteInternalUserById(id);
	}

	public User findUserByID(String ID) {
		// TODO Auto-generated method stub
		return dao.findUserByID(ID);
	}

	public User findUserByEmail(String email) {

		return dao.findUserByEmail(email);
	}

	public void updatePassword(String email, String password) {

		password = hashService.getBCryptHash(password);
		dao.updatePassword(email, password);

	}

	public Long generateRandomNumberOfLength(int length) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (ran.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (ran.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	public User searchExternalUser(String EmployeeID) {
		return dao.findExternalUserByID(EmployeeID);

	}

	public List<User> getPIIUsersService() {
		// TODO Auto-generated method stub
		return dao.getPIIUsers();
	}

	public void approvePIIUserModification(User user) {

		dao.approvePIIUserModification(user);

	}

	public void declinePIIUserModification(User user) {

		dao.declinePIIUserModification(user);

	}
}