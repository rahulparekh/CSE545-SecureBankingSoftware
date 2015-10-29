package com.sbs.group11.service;

import java.util.List;

import com.sbs.group11.model.ModifiedUser;
import com.sbs.group11.model.User;

public interface ModifiedService {
	void approveRequest(ModifiedUser modifieduser);
	void denyRequest(ModifiedUser modifieduser);
	ModifiedUser findModifiedUserByRequestID(String ID);
	List<ModifiedUser> getAllInternalModifiedUsers();
	List<ModifiedUser> getAllExternalModifiedUsers();
	void addRequest(ModifiedUser modifieduser);
}
