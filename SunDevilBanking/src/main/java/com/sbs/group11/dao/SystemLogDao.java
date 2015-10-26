package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.SystemLog;


public interface SystemLogDao {

	
	void addLog(SystemLog systemlog);
	public List<SystemLog> getAllLog();
}

