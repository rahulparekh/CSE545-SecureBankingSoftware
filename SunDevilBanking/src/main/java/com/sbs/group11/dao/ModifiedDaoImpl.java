package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.ModifiedUser;
import com.sbs.group11.model.User;
import com.sbs.group11.service.BCryptHashService;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.UserService;

@Repository("modifiedDao")
public class ModifiedDaoImpl extends AbstractDao<Integer, ModifiedUser> implements ModifiedDao {
	
@Autowired
InternalUserService internaluserservice;

@Autowired
private BCryptHashService hashService;

	@SuppressWarnings("unchecked")
	
	public ModifiedUser findModifiedUserByRequestID(String ID) {
		List<ModifiedUser> users = new ArrayList<ModifiedUser>();
		
		
		users = getSession()
				.createQuery("from ModifiedUser where RequestId=? order by RequestId")
				.setParameter(0, ID)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	
	public void approveRequest(ModifiedUser modifieduser) {
		// TODO Auto-generated method stub
		
		  modifieduser.setStatus("approve");
			
			User current_user = internaluserservice.findUserByID(modifieduser.getCustomerID());
			if(current_user != null)
			{
				System.out.println("Current User");
				current_user.setAddressLine1(modifieduser.getAddressLine1());
				current_user.setAddressLine2(modifieduser.getAddressLine2());
				current_user.setFirstName(modifieduser.getFirstName());
				current_user.setLastName(modifieduser.getLastName());
				if(!current_user.getPassword().equals(modifieduser.getPassword()))
				{	
					modifieduser.setPassword(hashService.getBCryptHash((modifieduser.getPassword())));
				}
				//current_user.setPassword(modifieduser.getPassword());
				current_user.setEmail(modifieduser.getEmail());
				current_user.setPhone(modifieduser.getPhone());
				current_user.setZipCode(modifieduser.getZipCode());
				current_user.setState(modifieduser.getState());
				current_user.setUserType(modifieduser.getUserType());
				current_user.setUpdatedAt(LocalDateTime.now());
				current_user.setEmployeeOverride(modifieduser.getEmployeeOverride());
				
				getSession().update(modifieduser);
				getSession().update(current_user);
			}
		}
	

	
	public void denyRequest(ModifiedUser modifieduser) {
		// TODO Auto-generated method stub
		modifieduser.setStatus("decline");
		getSession().update(modifieduser);
		
	}

	@SuppressWarnings("unchecked")
	public List<ModifiedUser> getAllInternalModifiedUsers() {
		
		List<ModifiedUser> users = new ArrayList<ModifiedUser>();
		
		users = getSession()
				.createQuery("from ModifiedUser where Status=? and UserType in (?,?)")
				.setParameter(0, "pending")
				.setParameter(1, "Manager")
				.setParameter(2, "Regular")
				.list();

		return users;
	}

	@SuppressWarnings("unchecked")
	
	public List<ModifiedUser> getAllExternalModifiedUsers() {
		// TODO Auto-generated method stub
		List<ModifiedUser> users = new ArrayList<ModifiedUser>();
		
		users = getSession()
				.createQuery("from ModifiedUser where Status=? and UserType in (?,?)")
				.setParameter(0, "pending")
				.setParameter(1, "Merchant")
				.setParameter(2, "Customer")
				.list();

		return users;
	}
	
	public void addRequest(ModifiedUser modifieduser)
	{
		
		persist(modifieduser);
	}

}
