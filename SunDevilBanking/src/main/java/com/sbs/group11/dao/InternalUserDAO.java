package com.sbs.group11.dao;

import java.util.List;
import com.sbs.group11.model.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;


 
@Repository("InternalUserDao")

public class InternalUserDAO extends AbstractDAO<Integer,InternalUser> {
 
public InternalUser getInternalUser( int id){
	return getByKey(id);
}

public void saveInternalUser(InternalUser user) {
    persist(user);
}

public void deleteInternalUserById(int id) {
    Query query = getSession().createSQLQuery("delete from Employee where EmployeeID = :id");
    query.setInteger(id, id);
    query.executeUpdate();
}

@SuppressWarnings("unchecked")
public List<InternalUser> getAllInternalUsers() {
    Criteria criteria = createEntityCriteria();
    return (List<InternalUser>) criteria.list();
}

}