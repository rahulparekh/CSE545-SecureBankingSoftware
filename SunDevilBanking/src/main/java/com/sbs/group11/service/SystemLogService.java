package com.sbs.group11.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.sbs.group11.dao.SystemLogDao;
import com.sbs.group11.model.SystemLog;
import com.sbs.group11.model.Transaction;


@Service("SystemLogService")
@Transactional
public class SystemLogService {


	@Autowired
    private SystemLogDao dao;
	
	public List<SystemLog> getAllLog() {
		return dao.getAllLog();
	}

	public void addLog(SystemLog systemlog){
		dao.addLog(systemlog);
	}
     
      
	
}
