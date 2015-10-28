package com.sbs.group11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.UserDao;
import com.sbs.group11.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserDao dao;
     
	public User getUserDetails(){
		String customerID = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
        	customerID = ((UserDetails)principal).getUsername();
        } else {
        	customerID = principal.toString();
        }
        
        return dao.findByCustomerID(customerID);
    }

	public List<User> getUsersOfType(String type) {
		return dao.getUsersOfType(type);
	}

	
	public User getUserbyCustomerID(String customerID){
		
		return dao.findByCustomerID(customerID);
	}



}

