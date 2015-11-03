package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import com.sbs.group11.controller.ExternalUserController;
import com.sbs.group11.model.User;

@Repository("internalUserDaoImpl")
public class InternalUserDaoImpl extends AbstractDao<Integer, User> implements InternalUserDao {
	
	final static Logger logger = Logger.getLogger(InternalUserDaoImpl.class);

	public void saveInternalUser(User user) {
		persist(user);
	}
	
	public void updateInternalUser(User user)
	{
		User current_user = findUserByID(user.getCustomerID());
		if(current_user != null)
		{
			current_user.setAddressLine1(user.getAddressLine1());
			current_user.setAddressLine2(user.getAddressLine2());
			current_user.setFirstName(user.getFirstName());
			current_user.setLastName(user.getLastName());
			//current_user.setPassword(user.getPassword());
			//current_user.setEmail(user.getEmail());
			current_user.setPhone(user.getPhone());
			current_user.setZipCode(user.getZipCode());
			current_user.setState(user.getState());
			current_user.setUserType(user.getUserType());
			current_user.setUpdatedAt(LocalDateTime.now());
		}
	}

	public void deleteInternalUserById(String id) {
		//Query query = getSession().createSQLQuery(
			//	"delete from User where CustomerID = :id");
		//query.setParameter("id", id);
		//query.executeUpdate();
		User user = findUserByID(id);
		getSession().delete(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllInternalUsers() {
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public User findInternalUserByID(String ID) {

		List<User> users = new ArrayList<User>();
		
		
		logger.info("findInternalUserByID called");
		

		users = getSession()
				.createQuery("from User where CustomerID=? and UserType in (?,?)")
				.setParameter(0, ID)
				.setParameter(1,"Regular")
				.setParameter(2,"Manager")
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public User findUserByType(String userType) {

		List<User> users = new ArrayList<User>();
		
		
		users = getSession()
				.createQuery("from User where UserType=?")
				.setParameter(0, userType)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	
	
	@SuppressWarnings("unchecked")
	public User findUserByEmail(String email) {

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

	public void updatePassword(String email,String password) {
		
		
		Query query = getSession().createSQLQuery(
				"update User set Password = :pwd where Email= :mail");
		query.setParameter("pwd", password);
		query.setParameter("mail", email);
		query.executeUpdate();

		
	}

	@SuppressWarnings("unchecked")
	
	public User findExternalUserByID(String ID) {
		List<User> users = new ArrayList<User>();
		
		logger.info("findExternalUserByID called");
		
		
		users = getSession()
				.createQuery("from User where CustomerID=? and UserType in (?,?)")
				.setParameter(0, ID)
				.setParameter(1,"Customer")
				.setParameter(2,"Merchant")
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")

	public User findUserByID(String ID) {
		List<User> users = new ArrayList<User>();
		
		
		users = getSession()
				.createQuery("from User where CustomerID=?")
				.setParameter(0, ID)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")

	public List<User> getPIIUsers() {
		List<User> users = new ArrayList<User>();
		
		logger.info("pii called");
			
		users = getSession()
				.createQuery("from User where UserType in (?,?) and PII = ?")
				.setParameter(0,"Customer")
				.setParameter(1,"Merchant")
				.setParameter(2,1)
				.list();

		if (users.size() > 0) {
			return users;
		} else {
			return null;
		}
	}

	public void approvePIIUserModification(User user) {
		
		user.setPII(1);
		getSession().update(user);
	}

	public void declinePIIUserModification(User user) {
		
		user.setPII(0);
		getSession().update(user);
		
	}


}