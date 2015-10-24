package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.User;

@Repository("internalUserDaoImpl")
public class InternalUserDaoImpl extends AbstractDao<Integer, User> implements InternalUserDao {

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
			current_user.setPassword(user.getPassword());
			current_user.setEmail(user.getEmail());
			current_user.setPhone(user.getPhone());
			current_user.setZipCode(user.getZipCode());
			current_user.setState(user.getState());
			current_user.setuserType(user.getuserType());
			current_user.setUpdatedAt(LocalDateTime.now());
		}
	}

	public void deleteInternalUserById(int id) {
		Query query = getSession().createSQLQuery(
				"delete from User where CustomerID = :id");
		query.setInteger(0, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllInternalUsers() {
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
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

	

}