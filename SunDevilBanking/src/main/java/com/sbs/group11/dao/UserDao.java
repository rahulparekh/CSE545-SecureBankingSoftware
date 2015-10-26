package com.sbs.group11.dao;

import com.sbs.group11.model.User;

public interface UserDao {
	
	User findByCustomerID(String customerID);

	User findByEmail(String email);

}