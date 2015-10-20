package com.sbs.group11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbs.group11.dao.InternalUserDAO;
import com.sbs.group11.model.User;

/**
 * InternalUserServiceImpl: Used to implement various methods which internal
 * users perform. Do not use this for general user methods like collecting the
 * user details or saving the user as those will be performed the user service.
 */
@Component
public class InternalUserServiceImpl implements InternalUserService {

	@Autowired
	private InternalUserDAO dao;

	public void addInternalUser(User user) {
		// Logic here to add a user
	}

}