package com.sbs.group11.service;

import java.util.List;

import com.sbs.group11.model.User;

public interface UserService {

	User getUserDetails();
	List<User> getUsersOfType(String type);
	public User getUserbyCustomerID(String customerID);	
}