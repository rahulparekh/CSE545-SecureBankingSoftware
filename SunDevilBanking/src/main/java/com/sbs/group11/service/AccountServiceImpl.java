package com.sbs.group11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.AccountDao;
import com.sbs.group11.model.Account;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
    private AccountDao dao;
     
	public List<Account> getAccountsByCustomerID(String customerID){
        
        return dao.findByCustomerID(customerID);
        
    }
}
