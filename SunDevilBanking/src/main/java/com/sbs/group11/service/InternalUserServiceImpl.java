package com.sbs.group11.service;

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
		
		Random ran = new Random();
		char[] digits = new char[11];
		digits[0] = (char) (ran.nextInt(9) + '1');
		for (int i = 1; i < 11; i++) {
			digits[i] = (char) (ran.nextInt(10) + '0');
		}
		String customerID = "" + Long.parseLong(new String(digits));
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
		
		dao.saveInternalUser(user);
	}
	
	public void updateInternalUser(User user) {
		// Logic here to add a user
		
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

}