package com.sbs.group11.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sbs.group11.dao.ModifiedDao;
import com.sbs.group11.model.ModifiedUser;
import com.sbs.group11.model.User;

@Component
@Service("ModifiedService")
@Transactional
public class ModifiedServiceImpl implements ModifiedService {

	@Autowired
	private ModifiedDao dao;


	public void approveRequest(ModifiedUser modifieduser) {
		// TODO Auto-generated method stub
		dao.approveRequest(modifieduser);
	}

	
	public void denyRequest(ModifiedUser modifieduser) {
		// TODO Auto-generated method stub
		dao.denyRequest(modifieduser);
	}

	
	public ModifiedUser findModifiedUserByRequestID(String ID) {
		return dao.findModifiedUserByRequestID(ID);
	}

	
	public List<ModifiedUser> getAllInternalModifiedUsers() {
		return dao.getAllInternalModifiedUsers();
	}

	
	public List<ModifiedUser> getAllExternalModifiedUsers() {
		
		return dao.getAllExternalModifiedUsers();
	}
	
	public void addRequest(ModifiedUser modifieduser)
	{
		dao.addRequest(modifieduser);
		
	}
}
	
