package com.sbs.group11.service;

import com.sbs.group11.dao.InternalUserDAO;
import com.sbs.group11.model.InternalUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Component
public class InternalUserService {
 
private InternalUserDAO internalUserDao;
 
public InternalUser getInternalUserById(int id) {
return internalUserDao.getInternalUser(id);
}
@Autowired
public void setInternalUserDao(InternalUserDAO internalUserDao) {
this.internalUserDao = internalUserDao;
}
 
public void addInternalUser(InternalUser user) {
internalUserDao.saveInternalUser(user);
}


 
public List<InternalUser> fetchAllInternalUsers() {
return internalUserDao.getAllInternalUsers();
}


}