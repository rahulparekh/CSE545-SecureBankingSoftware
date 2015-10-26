package com.sbs.group11.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sbs.group11.dao.SecurityQuesDaoImpl;
import com.sbs.group11.model.SecurityQues;

@Component
@Service("SecurityQuesService")
@Transactional
public class SecurityQuesServiceImpl implements SecurityQuesService {

	@Autowired
	private SecurityQuesDaoImpl dao;

	public SecurityQues findSecurityquesByEmail(String email) {
		
		 return dao.findSecurityquesByEmail(email);
	}
	
	public SecurityQues findSecurityquesByAnswer(String answer) {
		
		 return dao.findSecurityquesByAnswer(answer);
	}
}
