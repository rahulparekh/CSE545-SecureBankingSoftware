package com.sbs.group11.service;

import com.sbs.group11.model.User;

public interface InternalUserService {	
	void addInternalUser(User user);
	User searchInternalUser(String EmployeeID);
	User searchInternalUserByType(String userType);
	void updateInternalUser(User user);
}
