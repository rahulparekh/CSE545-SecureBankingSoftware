package com.sbs.group11.dao;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.sbs.group11.model.SystemLog;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("systemLogDaoImpl")
public class SystemLogDaoImpl extends AbstractDao<Integer,SystemLog>implements SystemLogDao{

	public void addLog(SystemLog systemlog) {
		getSession().save(systemlog);		
	}

	@SuppressWarnings("unchecked")
	public List<SystemLog> getAllLog() {
		
		List<SystemLog> logs = new ArrayList<SystemLog>();	
		logs = getSession()
				.createQuery("from SystemLog").list();
		
		/*String totallog = "";
		System.out.println(logs.get(0));
		for( int i=0; i< logs.size() ; i++){
			
			totallog = totallog + logs.get(i).toString() + '\n';
			
		}
		
					System.out.println(totallog);
		return totallog;*/
		
		return  logs;
		
		
	}	
		
}
