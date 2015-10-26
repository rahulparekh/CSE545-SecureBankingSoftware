package com.sbs.group11.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sbs.group11.model.SecurityQues;

@Repository("securityQuesDaoImpl")
public class SecurityQuesDaoImpl extends AbstractDao<Integer, SecurityQues> implements SecurityQuesDao{

	@SuppressWarnings("unchecked")
	public SecurityQues findSecurityquesByEmail(String email) {

		List<SecurityQues> securityques = new ArrayList<SecurityQues>();

		securityques = getSession()
			.createQuery("from SecurityQues where Email=?")
			.setParameter(0, email)
			.list();

		if (securityques.size() > 0) {
			return securityques.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public SecurityQues findSecurityquesByAnswer(String answer) {
		
		List<SecurityQues> securityques = new ArrayList<SecurityQues>();

		securityques = getSession()
			.createQuery("from SecurityQues where Answer1=?")
			.setParameter(0, answer)
			.list();

		if (securityques.size() > 0) {
			return securityques.get(0);
		} else {
			return null;
		}
	}
}

