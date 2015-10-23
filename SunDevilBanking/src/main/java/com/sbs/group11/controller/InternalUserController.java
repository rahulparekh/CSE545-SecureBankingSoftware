package com.sbs.group11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sbs.group11.model.*;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.TransactionService;

@Controller
public class InternalUserController {
	
	@Autowired
	InternalUserService internalUserService;
	@Autowired
	TransactionService transactionService;

	

	@ModelAttribute("user")
	public User getUserObject() {
		return new User();
	}
	
	@ModelAttribute("empSearch")
	public EmployeeSearch getempSearchObject() {
		return new EmployeeSearch();
	}
	
	@ModelAttribute("transactionSearch")
	public TransactionSearch getTransactionObject(){
		return new TransactionSearch();
	}

	@RequestMapping(value = "/manage-employee", method = RequestMethod.GET)
	public String getHome(ModelMap model,@ModelAttribute("user") User user) {
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("fullname", "Arun the Boss");
		return "employee/manage_employees";
	}

	@RequestMapping(value = "/manage-employee", method = RequestMethod.POST)
	public String AddInternalUser(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result) {
		System.out.println("inside controller");
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("user", "Sriram the Boss");
		System.out.println(user.getFirstName());
		model.addAttribute("user",new User());
		internalUserService.addInternalUser(user);
		
		return "employee/manage_employees";
	}
	
	@RequestMapping(value = "/sysadmin-home", method = RequestMethod.GET)
	public String getSysAdminHome(ModelMap model) {
		
		model.addAttribute("title", "Welcome System Admin:");	
		return "employee/home_sys_admin";
		
	}
	
	
	
	@RequestMapping(value = "/sysadmin-home", method = RequestMethod.POST)
	public String SearchInternalUser(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result) {
		
		//model.addAttribute("title", "Welcome System Admin:");
		User user = internalUserService.searchInternalUser(empSearch.getEmployeeID());
		System.out.println(user.getFirstName());
		model.addAttribute("user", user);
		
		return "redirect:/manage-employee";
	}
	
	@RequestMapping(value = "/internalemployee-home" , method = RequestMethod.GET)
     public String getInternalEmployeeHome(ModelMap model){
		
		  System.out.println("HI i am here");
        // TODO : add the internal employee's name
    	 return "employee/home_int_employee";
	}
	
	@RequestMapping(value = "/internalemployee-home", method = RequestMethod.POST)
	public String SearchTransactions(ModelMap model, @ModelAttribute("transactionSearch")
	TransactionSearch transactionSearch, BindingResult result){
		
		System.out.print("Arun:"+transactionSearch.getTransactionID());
		Transaction transaction = transactionService.getTransaction(transactionSearch.getTransactionID());
		System.out.print("Hi" + transaction.getTransactionID());
		return "employee/home_int_employee";
	}
	
	@RequestMapping(value = "/internalemployee-pendingtransaction", method = RequestMethod.GET)
	public String getPendingTransactions(ModelMap model){
		
		List<Transaction> pendingTransaction = transactionService.getPendingTransactions();
		System.out.println("Pending Transaction" + pendingTransaction.get(0).getTransactionID());
		return "employee/int_employee_pending_transaction";
		
	}
}