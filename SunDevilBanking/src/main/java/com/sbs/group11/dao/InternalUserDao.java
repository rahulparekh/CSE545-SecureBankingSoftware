package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.User;

public interface InternalUserDao {
	
	void saveInternalUser(User user);
	
	List<User> getAllInternalUsers();
	
	User findUserByID(String ID);
	User findInternalUserByID(String ID);
	User findExternalUserByID(String ID);
	User findUserByType(String userType);
	User findUserByEmail(String email);
	
	void updateInternalUser(User user);
	void deleteInternalUserById(String id);
	void updatePassword(String email, String password);
	public void approvePIIUserModification (User user);
	public void declinePIIUserModification (User user);
	
	List <User> getPIIUsers();
	
	

}
