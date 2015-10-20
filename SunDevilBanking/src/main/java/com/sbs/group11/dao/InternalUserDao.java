package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.User;

public interface InternalUserDao {
	
	void saveInternalUser(User user);
	
	void deleteInternalUserById(int id);
	
	List<User> getAllInternalUsers();

}
