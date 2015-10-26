package com.sbs.group11.dao;

import com.sbs.group11.model.SecurityQues;


public interface SecurityQuesDao {

	SecurityQues findSecurityquesByEmail(String email);
	
	SecurityQues findSecurityquesByAnswer(String answer);
	
}
