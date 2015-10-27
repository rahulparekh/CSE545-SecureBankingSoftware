package com.sbs.group11.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.Log;
import com.sbs.group11.dao.InternalUserDaoImpl;
import com.sbs.group11.dao.UserDaoImpl;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.Role;
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
	
	final static Logger logger = Logger.getLogger(InternalUserServiceImpl.class);

	@Autowired
	private InternalUserDaoImpl dao;
	
	@Autowired
	private BCryptHashService hashService;
	

	public void addInternalUser(User user) {
		// Logic here to add a user		
		
		String customerID = "" + generateRandomNumberOfLength(11);
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
		
		
		Set<Account> accounts = new HashSet<Account>();
		Account account1 = new Account();
		Account account2 = new Account();
		String checking_account = "" + generateRandomNumberOfLength(17);
		String saving_account = "" + generateRandomNumberOfLength(17);
		
		account1.setNumber(checking_account);
		account1.setBalance(new BigDecimal(0.0));
		account1.setCreatedAt(LocalDateTime.now());
		account1.setUser(user);
		account1.setName(user.getFirstName()+" checking account");
		account1.setType(0);
		account1.setUpdatedAt(LocalDateTime.now());
		

		account2.setNumber(saving_account);
		account2.setBalance(new BigDecimal(0.0));
		account2.setCreatedAt(LocalDateTime.now());
		account2.setUser(user);
		account2.setName(user.getFirstName()+" saving account");
		account2.setType(1);
		account2.setUpdatedAt(LocalDateTime.now());
		
		accounts.add(account1);
		accounts.add(account2);
		
		user.setAccounts(accounts);
		
		
		dao.saveInternalUser(user);
	}
	
	public void updateInternalUser(User user) {
		// Logic here to add a user
		User current_user = findUserByEmail(user.getEmail());
		if(!current_user.getPassword().equals(user.getPassword()))
		{	
			user.setPassword(hashService.getBCryptHash((user.getPassword())));
		}
		
		dao.updateInternalUser(user);
	}
	
	public User searchInternalUser(String EmployeeID)
	{
		return dao.findUserByID(EmployeeID);
		
	}
	
	public User searchInternalUserByType(String userType)
	{
		return dao.findUserByType(userType);
		
	}
	
	
		
	
	public  void deleteInternalUserById(String id){
		// TODO Auto-generated method stub
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
	
		dao.updatePassword(email,password);
		
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

}