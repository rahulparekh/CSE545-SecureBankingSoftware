package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
	
	final static Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public User findByCustomerID(String customerID) {

		List<User> users = new ArrayList<User>();
		
		users = getSession()
				.createQuery("from User where CustomerID=?")
				.setParameter(0, customerID)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public User findByEmail(String email) {

		List<User> users = new ArrayList<User>();

		users = getSession()
			.createQuery("from User where Email=?")
			.setParameter(0, email)
			.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersOfType(String type) {
		List<User> users = new ArrayList<User>();

		users = getSession()
			.createQuery("from User where UserType=:type")
			.setParameter("type", type)
			.list();
		
		return users;
	}

}