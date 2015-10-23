package com.sbs.group11.service;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sbs.group11.dao.InternalUserDao;
import com.sbs.group11.dao.InternalUserDaoImpl;
import com.sbs.group11.model.User;

import java.util.Random;
/*
 * InternalUserServiceImpl: Used to implement various methods which internal
 * users perform. Do not use this for general user methods like collecting the
 * user details or saving the user as those will be performed the user service.
 */
@Component
@Service("InternalUserService")
@Transactional
public class InternalUserServiceImpl implements InternalUserService {

	@Autowired
	private InternalUserDaoImpl dao;

	public void addInternalUser(User user) {
		// Logic here to add a user
		
		
			Random ran = new Random();
			int x = ran.nextInt(10) + 5;
			String customerID = ""+x;
			user.setCustomerID(customerID);
			user.setCreatedAt(LocalDateTime.now());
			user.setLastLoginAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
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

}