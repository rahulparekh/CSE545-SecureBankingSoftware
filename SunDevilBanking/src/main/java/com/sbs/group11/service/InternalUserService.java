package com.sbs.group11.service;

import java.util.List;

import com.sbs.group11.model.User;

public interface InternalUserService {	
	void addInternalUser(User user);
	User searchInternalUser(String EmployeeID);
	User searchInternalUserByType(String userType);
	void updateInternalUser(User user);
	void deleteInternalUserById(String id);
	User findUserByID(String ID);
	User findUserByEmail(String email);
	void updatePassword(String email,String password);
	Long generateRandomNumberOfLength(int length);
	User searchExternalUser(String EmployeeID);
	public void approvePIIUserModification (User user);
	public void declinePIIUserModification (User user);

	List <User> getPIIUsersService();
	
} 
