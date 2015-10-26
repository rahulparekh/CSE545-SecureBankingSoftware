package com.sbs.group11.service;

import com.sbs.group11.model.SecurityQues;


public interface SecurityQuesService {
	
	SecurityQues findSecurityquesByEmail(String Email);
	SecurityQues findSecurityquesByAnswer(String answer);

}
