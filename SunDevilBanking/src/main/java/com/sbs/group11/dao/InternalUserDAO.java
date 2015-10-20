package com.sbs.group11.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sbs.group11.model.User;

@Repository("internalUserDao")
public class InternalUserDAO extends AbstractDao<Integer, User> {

	public void saveInternalUser(User user) {
		persist(user);
	}

	public void deleteInternalUserById(int id) {
		Query query = getSession().createSQLQuery(
				"delete from Employee where EmployeeID = :id");
		query.setInteger(id, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllInternalUsers() {
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
	}

}