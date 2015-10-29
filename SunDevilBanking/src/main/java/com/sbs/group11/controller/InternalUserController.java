package com.sbs.group11.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.*;
import com.sbs.group11.service.AccountService;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.SystemLogService;
import com.sbs.group11.service.ModifiedService;
import com.sbs.group11.service.TransactionService;
import com.sbs.group11.service.UserService;

@Controller
public class InternalUserController {
	
	@Autowired
	InternalUserService internalUserService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	SystemLogService systemLogService;
	@Autowired
	ModifiedService modifiedService;
	@Autowired
	AccountService accountService;
	@Autowired

	UserService userService;
	@Autowired
	SmartValidator validator;
	
	
	final private BigDecimal CRITICAL_VALUE = new BigDecimal(500);

	
	
	final static Logger logger = Logger.getLogger(InternalUserController.class);

	@ModelAttribute("user")
	public User getUserObject() {
		return new User();
	}
	
	@ModelAttribute("systemlogs")
	public SystemLog getSystemLogObject() {
		return new SystemLog();
	}
	
	@ModelAttribute("empSearch")
	public EmployeeSearch getempSearchObject() {
		return new EmployeeSearch();
	}
	
	@ModelAttribute("transactionSearch")
	public TransactionSearch getTransactionObject(){
		return new TransactionSearch();
	}
	@ModelAttribute("modifiedUser")
	public ModifiedUser getModifiedUser() {
		return new ModifiedUser();
	}
	
	
	@ModelAttribute("ModificationTransaction")
	public  ModificationTransaction getModificationTransaction(){
		return new ModificationTransaction();
	}

	//**************************PII starts************************************
	@RequestMapping(value = "/government-home", method = RequestMethod.GET)
	public String getGovernemntHomePage(ModelMap model) {
		
		
		model.addAttribute("title" , "Welcome Government authority");		
		return "employee/home_government";
	}
	
	@RequestMapping(value = "/government-home", method = RequestMethod.POST)
	public String SearchInternalUserbyGovAuthority(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result,RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchExternalUser(empSearch.getEmployeeID());
		if (user == null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"Not a valid User");
			return "redirect:/government-home";
		}
		
		
		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/viewcustomer-government";
	}
	
	@RequestMapping(value = "/viewcustomer-government", method = RequestMethod.GET)
	public String getGovermentViewCustomerPage(ModelMap model , @ModelAttribute("user") User user) {
		model.addAttribute("PIIUser", user);
		model.addAttribute("title" , "Welcome Government authority");		
		return "employee/approve_govt";
	}
	
	
	
		@RequestMapping(value = "/approve-modification-govt", method = RequestMethod.POST)
		public String approveModificationGOVT(ModelMap model,
				HttpServletRequest request) {
			System.out.println("See this " + request.getParameter("PIImodifiedUserID"));
			User user = internalUserService.findUserByID(request.getParameter("PIImodifiedUserID"));
			System.out.println("Customer id" + user.getCustomerID());
			internalUserService.approvePIIUserModification(user);
			return "redirect:/government-home";
			
		}
		
		
		@RequestMapping(value = "/decline-govt", method = RequestMethod.POST)
		public String DeclineModificationGOVT(ModelMap model,
				HttpServletRequest request) {
			
			User user = internalUserService.findUserByID(request.getParameter("PIImodifiedUserID"));
			
			internalUserService.declinePIIUserModification(user);
			return "redirect:/government-home";
			
		}
		
		
		
		@RequestMapping(value = "/back-modification-govt", method = RequestMethod.POST)
		public String BackModificationGOVT(ModelMap model,
				HttpServletRequest request) {
			
			
			return "redirect:/government-home";
			
		}
			
		//**************************PII ends************************************
	
	
	
	
	
	@RequestMapping(value = "/sysadmin-home", method = RequestMethod.GET)
	public String getSysAdminHome(ModelMap model) {
		
		model.addAttribute("title", "Welcome System Admin:");	
		return "employee/home_sys_admin";
		
	}
	
	@RequestMapping(value = "/sysadmin-home", method = RequestMethod.POST)
	public String SearchInternalUser(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result,RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchInternalUser(empSearch.getEmployeeID());
		if (user == null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"Not a valid User");
			return "redirect:/sysadmin-home";
		}
		
		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/edit-employee";
	}
		
	
	@RequestMapping(value = "/edit-employee", method = RequestMethod.GET)
	public String getEditUserInfo(ModelMap model,@ModelAttribute("user") User user) {
		Map<String,String> userTypes = new LinkedHashMap<String,String>();
		System.out.println("user name is"+user.getCustomerID());
		userTypes.put("regular", "regular");
		userTypes.put("manager", "manager");
		model.addAttribute("user", user);
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_employees_edt";
	}
	
	
	@RequestMapping(value = "/edit-employee_success", method = RequestMethod.POST)
	public String AddInternalUser(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result) {
		internalUserService.updateInternalUser(user);
		return "redirect:/sysadmin-home";
	}
	
	
	@RequestMapping(value = "/manage-employee", method = RequestMethod.GET)
	public String addUserInfo(ModelMap model,@ModelAttribute("user") User user) {
		Map<String,String> userTypes = new LinkedHashMap<String,String>();
		userTypes.put("regular", "regular");
		userTypes.put("manager", "manager");
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_employees";
	}
	
	@RequestMapping(value = "/manage-employee_success", method = RequestMethod.POST)
	public String AddExternalUserpost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		System.out.println("inside controller");
		model.addAttribute("user",new User());
		if(internalUserService.findUserByEmail(user.getEmail())!= null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"User Already Exists with the same Email Address");
			return "redirect:/sysadmin-home"; 
		}
		internalUserService.addInternalUser(user);
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),user.getFirstName(),user.getUserType(),"The user "+user.getFirstName()+" is created");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"User Added Successfully");
		return "redirect:/sysadmin-home"; 
	}
	
	@RequestMapping(value = "/employee_success", params="update" ,method = RequestMethod.POST)
	public String addUserInfoPost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		model.addAttribute("user", user);
		model.addAttribute("user",new User());
		internalUserService.updateInternalUser(user);
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),user.getFirstName(),user.getUserType(),"The user "+user.getFirstName()+" info is updated");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"UserInformation updated Successfully");
		return "redirect:/sysadmin-home";
	}
	

	@RequestMapping(value = "/employee_success", params = "delete" ,method = RequestMethod.POST)
	public String deleteUserInfoPost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		model.addAttribute("user", user);
		model.addAttribute("user",new User());
		internalUserService.deleteInternalUserById(user.getCustomerID());
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),user.getFirstName(),user.getUserType(),"The user "+user.getFirstName()+" is deleted");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"Deleted the user Successfully");
		return "redirect:/sysadmin-home";
	}
	
	
	@RequestMapping(value = "/sysadmin-setting", method = RequestMethod.GET)
	public String getAdminSetting(ModelMap model,@ModelAttribute("user") User user) {
		
		User sysadmin = userService.getUserDetails();
		model.addAttribute("user", sysadmin);
		return "employee/setting_sys_admin";
	}
		@RequestMapping(value = "/sysadmin-setting_success", method = RequestMethod.POST)
	public String changeAdminSetting(ModelMap model,
			@ModelAttribute("user") User user,BindingResult result) {
		
		System.out.println("id is "+user.getCustomerID());
		user.setUserType("admin");
		internalUserService.updateInternalUser(user);
		return "redirect:/sysadmin-home";
	}


	
	//********************************MANAGER**************************		
	
	@RequestMapping(value = "/manager-home", method = RequestMethod.GET)
	public String getManagerHome(ModelMap model) {
		
		model.addAttribute("title", "Welcome Manager:");	
		return "employee/home_manager";
		
	}
	
	@RequestMapping(value = "/manager-customer-search", method = RequestMethod.GET)
	public String getCustomerSearch(ModelMap model) {
		return "employee/manager_customer_search";
	}

	
	@RequestMapping(value = "/manager-customer-search", method = RequestMethod.POST)
	public String SearchInternalUserManager(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result,RedirectAttributes redirectAttrs) {
		
		User user = internalUserService.searchExternalUser(empSearch.getEmployeeID());
		if (user == null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"Not a valid User");
			return "redirect:/manager-customer-search";
		}
		
		System.out.println(user.getFirstName());
		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/edit-customer";
	}
	
	
	
	@RequestMapping(value = "/edit-customer", method = RequestMethod.GET)
	public String getEditUserInfoManager(ModelMap model,@ModelAttribute("user") User user) {
		Map<String,String> userTypes = new LinkedHashMap<String,String>();
		System.out.println("user name is"+user.getCustomerID());
		userTypes.put("customer", "customer");
		userTypes.put("merchant", "merchant");
		model.addAttribute("user", user);
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_customers_edt";
	}
	
	
	

	@RequestMapping(value = "/customer_success", params = "delete" ,method = RequestMethod.POST)
	public String deleteUserInfoPostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		System.out.println("inside controller");
		model.addAttribute("user",new User());
		internalUserService.deleteInternalUserById(user.getCustomerID());
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"Deleted the user Successfully");
		return "redirect:/manager-customer-search";
	}	
	
	

	@RequestMapping(value = "/manage-customer", method = RequestMethod.GET)
	public String addUserInfoManager(ModelMap model,@ModelAttribute("user") User user) {
		Map<String,String> userTypes = new LinkedHashMap<String,String>();
		System.out.println("user name is"+user.getCustomerID());
		userTypes.put("customer", "customer");
		userTypes.put("merchant", "merchant");
		model.addAttribute("user", user);
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_customers";
	}
	
	@RequestMapping(value = "/manage-customer_success", method = RequestMethod.POST)
	public String AddExternalUserpostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		System.out.println("inside controller");
		model.addAttribute("user",new User());
		if(internalUserService.findUserByEmail(user.getEmail())!= null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"User Already Exists with the same Email Address");
			return "redirect:/manager-customer-search"; 
		}
		internalUserService.addInternalUser(user);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"Added the User Successfully");
		return "redirect:/manager-customer-search";
	}
	
	
	


	@RequestMapping(value = "/internalemployee-pendingtransaction", method = RequestMethod.GET)
	public String getPendingTransactions(ModelMap model){
		User user = userService.getUserDetails();
		List<Transaction> pendingTransaction = transactionService.getPendingTransactions();
		//System.out.println("Pending Transaction" + pendingTransaction.get(0).getTransactionID());
		model.addAttribute("pendingTransaction", pendingTransaction);
			
			return "employee/int_employee_pending_transaction";
		
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public String TransactionApprove( ModelMap model,
			  HttpServletRequest request, RedirectAttributes attr) {
	    
		boolean result = transactionService.approveTransaction(request.getParameter("transactionID"));
		
		System.out.print(result);
		if(!result){
		attr.addFlashAttribute(
				"failureMsg",
				"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
		}
		
		return "redirect:/internalemployee-pendingtransaction";
					
	}
	
	@RequestMapping(value = "/manager-setting", method = RequestMethod.GET)
	public String getManagerSetting(ModelMap model,@ModelAttribute("user") User user) {
		
		System.out.println("inside get");
		User manager = userService.getUserDetails();
		model.addAttribute("user", manager);
		return "employee/setting_manager";
	}
		@RequestMapping(value = "/manager-setting_success", method = RequestMethod.POST)
	public String changeManagerSetting(ModelMap model,
			@ModelAttribute("user") User user,BindingResult result) {
		
		System.out.println("inside post");
		User current_user= userService.getUserDetails();
		System.out.println("id is "+current_user.getCustomerID());
		ModifiedUser modifiedUser = new ModifiedUser();
		modifiedUser.setCustomerID(current_user.getCustomerID());
		modifiedUser.setAddressLine1(user.getAddressLine1());
		modifiedUser.setAddressLine2(user.getAddressLine2());
		modifiedUser.setPassword(user.getPassword());
		modifiedUser.setEmail(current_user.getEmail());
		modifiedUser.setEmployeeOverride(current_user.getEmployeeOverride());
		modifiedUser.setEnabled(current_user.getEnabled());
		modifiedUser.setFirstName(user.getFirstName());
		modifiedUser.setLastName(user.getLastName());
		modifiedUser.setMiddleName(user.getMiddleName());
		modifiedUser.setState(user.getState());
		modifiedUser.setZipCode(user.getZipCode());
		modifiedUser.setuserType(current_user.getUserType());
		modifiedUser.setPhone(user.getPhone());
		modifiedUser.setCreatedAt(current_user.getCreatedAt());
		modifiedUser.setUpdatedAt(current_user.getUpdatedAt());
		modifiedUser.setLastLoginAt(current_user.getLastLoginAt());
		modifiedUser.setStatus("pending");
		long epoch = System.currentTimeMillis()/1000;
		modifiedUser.setRequestid(epoch);
		modifiedService.addRequest(modifiedUser);
		//internalUserService.updateInternalUser(user);
		
		return "redirect:/manager-home";
	}
	
	
	

	///***************EXTERNAL REQUESTS
	
	@RequestMapping(value = "/requests-pending-ext", method = RequestMethod.GET)
	public String getAdminSettingManager(ModelMap model) {
		
		List <ModifiedUser> musers = modifiedService.getAllExternalModifiedUsers();
		model.addAttribute("pendingModifications" , musers);		
		return "employee/pendingRequest_ext";
	}
	
	@RequestMapping(value = "/requests-approve-ext", method = RequestMethod.GET)
	public String getApprovalPageManager(ModelMap model) {
		return "employee/approve_requests_ext";
	}
	
	@RequestMapping(value = "/requests-view-ext", method = RequestMethod.POST)
	public String postApprovalePageManager(ModelMap model,
			HttpServletRequest request){
	
		System.out.println(request.getParameter("modificationuserID"));
		ModifiedUser modifiedUser = 
				modifiedService.findModifiedUserByRequestID(request.getParameter("modificationrequestid"));
		System.out.println("request id "+modifiedUser.getRequestid());
		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests_ext";
	}
	
		@RequestMapping(value = "/approve-modification-ext", method = RequestMethod.POST)
		public String approveModificationManager(ModelMap model,
				HttpServletRequest request) {
			System.out.println("See this " + request.getParameter("modifiedUserId"));
			ModifiedUser modifieduser = modifiedService.findModifiedUserByRequestID(request.getParameter("modifiedrequestid")); 
			System.out.println("Customer id" + modifieduser.getCustomerID());
			modifiedService.approveRequest(modifieduser);
			return "redirect:/requests-pending-ext";
			
		}
			
		@RequestMapping(value = "/decline-modification-ext", method = RequestMethod.POST)
		public String declineModificationManager(ModelMap model,
				 HttpServletRequest request) {
			System.out.print("Inside decline transaction");
			ModifiedUser modifieduser = modifiedService.findModifiedUserByRequestID(request.getParameter("modifiedrequestid"));
			modifiedService.denyRequest(modifieduser);
			
			return "redirect:/requests-pending-ext";
		}
		
		@RequestMapping(value = "/back-modification-ext", method = RequestMethod.POST)
		public String backModificationManager(ModelMap model) {
			System.out.print("HI");
			return "redirect:/requests-pending-ext";
		}
	

	
	@RequestMapping(value = "/decline", method = RequestMethod.POST)
	public String TransactionDecline( ModelMap model,
			  HttpServletRequest request) {
	    
		transactionService.declineTransaction(request.getParameter("transactionID"));
		
		return "redirect:/internalemployee-pendingtransaction";
			
	}
	
	//
	@RequestMapping(value = "/modificiation-transction-approve", method = RequestMethod.POST)
	public String ModificationTransactionApprove( ModelMap model,
			@ModelAttribute("modificationTransaction") ModificationTransaction modificationTransaction,HttpServletRequest request,
			RedirectAttributes attr) {
	    
		
	try{
		
		Transaction transaction = transactionService.getTransaction(request.getParameter("modifytransactionID"));
		System.out.println("Amount test "+modificationTransaction.getAmount() + "SenderNumber "+ modificationTransaction.getSenderAccNumber());
		
		if(modificationTransaction.getAmount()!=null){
			
			
			
				BigDecimal amount = new BigDecimal(modificationTransaction.getAmount());
		
			
				BigDecimal zeroamount = new BigDecimal(0);
					if((amount.compareTo(zeroamount)==0) || (amount.compareTo(zeroamount)==-1)){
				
							attr.addFlashAttribute(
						"failureMsg","Amount should be greater than zero");
							return "redirect:/internalemployee-pendingtransaction";
				
					}
			
			
			
					transaction.setAmount(amount);
					transaction.setSenderAccNumber(modificationTransaction.getSenderAccNumber());
					transaction.setReceiverAccNumber(modificationTransaction.getRecieverAccNumber());
		
			
					Account accountSender = accountService.getAccountByNumber(modificationTransaction.getSenderAccNumber());
			
			if(accountSender==null){
				
				attr.addFlashAttribute(
						"failureMsg","Invalid AccountNumber");
				return "redirect:/internalemployee-pendingtransaction";
			}
			
			Account accountReciever = accountService.getAccountByNumber(modificationTransaction.getRecieverAccNumber());
			
			
			if(accountReciever == null){
				
				attr.addFlashAttribute(
						"failureMsg","Invalid AccountNumber");
				return "redirect:/internalemployee-pendingtransaction";
				
			}
			
			User user = userService.getUserbyCustomerID(accountSender.getUser().getCustomerID());  
			if(user.getEmployeeOverride()==1){
				
		
				boolean result = transactionService.approveTransactionafterModification(transaction);
		
				System.out.print(result);
				if(!result){
					attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
				}
				else{
					
					attr.addFlashAttribute(
							"sucessMsg",
							"Transaction Approved Sucessfully with modifications");
				}
		   }else{
			   
			   attr.addFlashAttribute("failureMsg","You may not have the neccessary permissions to approve this transaction");
		   }
		
		
		
		}else{
			  
			attr.addFlashAttribute("failureMsg","Please put to a valid amount to process this transaction");
			model.addAttribute("modificationTransaction", modificationTransaction);
			return "redirect:/internalemployee-pendingtransaction";
		}
		
    
		
		return "redirect:/internalemployee-pendingtransaction";
		
		 }catch(Exception e ){
				
			 attr.addFlashAttribute("failureMsg","Please put to a valid amount to process this transaction");
				model.addAttribute("modificationTransaction", modificationTransaction);
				return "redirect:/internalemployee-pendingtransaction";
			}
	}
	
	
	@RequestMapping(value = "/modificiation-critical-transction-approve", method = RequestMethod.POST)
	public String ModificationCriticalTransactionApprove( ModelMap model,
			@ModelAttribute("modificationTransaction") ModificationTransaction modificationTransaction,HttpServletRequest request,
			RedirectAttributes attr) {
	    
		
		
	try{
		
		Transaction transaction = transactionService.getTransaction(request.getParameter("modifytransactionID"));
		System.out.println("Amount test "+modificationTransaction.getAmount() + "SenderNumber "+ modificationTransaction.getSenderAccNumber());
		
		if(modificationTransaction.getAmount()!=null){
			BigDecimal amount = new BigDecimal(modificationTransaction.getAmount());
			BigDecimal zeroamount = new BigDecimal(0);
			if((amount.compareTo(zeroamount)==0) || (amount.compareTo(zeroamount)==-1)){
				
				attr.addFlashAttribute(
						"failureMsg","Amount should be greater than zero");
					return "redirect:/internalemployee-pending-critical-transaction";
				
			}
			
			
			
			transaction.setAmount(amount);
			transaction.setSenderAccNumber(modificationTransaction.getSenderAccNumber());
			transaction.setReceiverAccNumber(modificationTransaction.getRecieverAccNumber());
		
			
			Account accountSender = accountService.getAccountByNumber(modificationTransaction.getSenderAccNumber());
			
			if(accountSender==null){
				
					attr.addFlashAttribute(
						"failureMsg","Invalid AccountNumber");
					return "redirect:/internalemployee-pending-critical-transaction";
			}
			
			Account accountReciever = accountService.getAccountByNumber(modificationTransaction.getRecieverAccNumber());
			
			
			if(accountReciever == null){
				
					attr.addFlashAttribute(
						"failureMsg","Invalid AccountNumber");
					return "redirect:/internalemployee-pending-critical-transaction";
				
			}
			
			User user = userService.getUserbyCustomerID(accountSender.getUser().getCustomerID());  
			if(user.getEmployeeOverride()==1){
				
		
				boolean result = transactionService.approveTransactionafterModification(transaction);
		
				System.out.print(result);
				if(!result){
					attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
				}
				else{
					
					attr.addFlashAttribute(
							"sucessMsg",
							"Transaction Approved Sucessfully with modifications");
				}
		   }else{
			   
			   attr.addFlashAttribute("failureMsg","You may not have the neccessary permissions to approve this transaction");
		   }
		
		
		
		}else{
			  
			attr.addFlashAttribute("failureMsg","Please put to a valid amount to process this transaction");
			model.addAttribute("modificationTransaction", modificationTransaction);
			return "redirect:/internalemployee-pending-critical-transaction";
		}
		
		return "redirect:/internalemployee-pending-critical-transaction";
	}catch(Exception e){
		
		attr.addFlashAttribute("failureMsg","Please put to a valid amount to process this transaction");
		model.addAttribute("modificationTransaction", modificationTransaction);
		return "redirect:/internalemployee-pending-critical-transaction";
	}
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/get-list-of-accounts-for-transaction", method = RequestMethod.GET)
	public String searchTransactionsbyAccounts(ModelMap model, HttpServletRequest request){
	
		return "employee/home_int_employee";
	}
	
	@RequestMapping(value = "/get-list-of-accounts-for", method = RequestMethod.POST)
	public String getAccountsforaCustomer(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr){
		
		System.out.println("Hi welcome to get");
		// Overrrrride check
		User user = userService.getUserbyCustomerID(request.getParameter("customerID"));
		
			if(user==null){
				
				attr.addFlashAttribute(
						"failureMsg",
						"The CustomerID entered is either Invalid or does exist");
				return "redirect:/get-list-of-accounts-for-transaction";
				
			}
		
		
		
		if(user.getEmployeeOverride()==1){
			List<Account> accounts = accountService.getAccountsByCustomerID(request.getParameter("customerID"));
			model.addAttribute("accounts", accounts);
			return "employee/home_int_employee";
		}
		else{
			//Flash Attribute
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			return "redirect:/get-list-of-accounts-for-transaction";
			
		}
		
	}
	
	
	@RequestMapping(value = "/getTransactionforAccount", method = RequestMethod.GET)
	public String getTransactionsforAccounts(ModelMap model,HttpServletRequest request){
		
		
		
		List<Transaction> transactions = transactionService.getTransactionsForAccountNumber(request.getParameter("accNumber"));
		model.addAttribute("transactions", transactions);
		return "employee/int_employee_show_transactions";
	}
	
	
	@RequestMapping(value = "/get-transaction-for-transactionID", method = RequestMethod.GET)
	public String getTransactionforTransactionID(ModelMap model,HttpServletRequest request,
			RedirectAttributes attr){
		
		// Overrrrrrride Check
		Transaction transaction = transactionService.getTransaction(request.getParameter("transactionID"));
		
		if(transaction==null){
			
			attr.addFlashAttribute(
					"failureMsg",
					"TransactionID Invalid or Not Found");
			return "redirect:/get-list-of-accounts-for-transaction";
		}
		
		
		Account account = accountService.getAccountByNumber(transaction.getSenderAccNumber());
		User user = userService.getUserbyCustomerID(account.getUser().getCustomerID());
		
		if(user.getEmployeeOverride()==1){
			model.addAttribute("transaction", transaction);
			return "employee/int_employee_show_single_transaction";
		}
		else{
			// Flash attribute
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			 return "redirect:/get-list-of-accounts-for-transaction";
		}
		
		
	}
	
	@RequestMapping(value = "/internalemployee-pending-critical-transaction", method = RequestMethod.GET)
	public String getPendingCriticalTransactions(ModelMap model){
		
		List<Transaction> pendingTransaction = transactionService.getPendingCriticalTransaction();
		model.addAttribute("pendingCriticalTransaction", pendingTransaction);
		return "employee/int_employee_pending_critical_transaction";

	}
	
	@RequestMapping(value = "/critical-approve", method = RequestMethod.POST)
	public String CriticalTransactionApprove( ModelMap model,
			  HttpServletRequest request, RedirectAttributes attr) {
	    
		boolean result = transactionService.approveTransaction(request.getParameter("transactionID"));
		
		System.out.print(result);
		if(!result){
		attr.addFlashAttribute(
				"failureMsg",
				"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
		}
		
		return "redirect:/internalemployee-pending-critical-transaction";
		
			
	}

	/////**************INTERNAL REQUESTS
	
	@RequestMapping(value = "/requests-pending", method = RequestMethod.GET)
	public String getAdminSettingSysAdmin(ModelMap model) {
		
		List <ModifiedUser> musers = modifiedService.getAllInternalModifiedUsers();
		model.addAttribute("pendingModifications" , musers);		
		return "employee/pendingRequest";
	}
	
	@RequestMapping(value = "/requests-approve", method = RequestMethod.GET)
	public String getApprovalPage(ModelMap model) {
		return "employee/approve_requests";
	}
	
	@RequestMapping(value = "/requests-view", method = RequestMethod.POST)
	public String postApprovalePage(ModelMap model,
			HttpServletRequest request){
	
		System.out.println(request.getParameter("modificationuserID"));
		ModifiedUser modifiedUser = 
				modifiedService.findModifiedUserByRequestID(request.getParameter("modificationrequestid"));
		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests";
	}
	
		@RequestMapping(value = "/approve-modification", method = RequestMethod.POST)
		public String approveModification(ModelMap model,
				HttpServletRequest request) {
			System.out.println("See this " + request.getParameter("modifiedUserId"));
			ModifiedUser modifieduser = modifiedService.findModifiedUserByRequestID(request.getParameter("modifiedrequestid"));
			System.out.println("Customer id" + modifieduser.getCustomerID());
			modifiedService.approveRequest(modifieduser);
			return "redirect:/requests-pending";
			
		}
			
		@RequestMapping(value = "/decline-modification", method = RequestMethod.POST)
		public String declineModification(ModelMap model,
				 HttpServletRequest request) {
			System.out.print("Inside decline transaction");
			ModifiedUser modifieduser = modifiedService.findModifiedUserByRequestID(request.getParameter("modifiedrequestid"));
			modifiedService.denyRequest(modifieduser);
			
			return "redirect:/requests-pending";
		}
		
		@RequestMapping(value = "/back-modification", method = RequestMethod.POST)
		public String backModification(ModelMap model) {
			System.out.print("HI");
			return "redirect:/requests-pending";
		}


	//**********************INTERNAL EMPLOYEE/REGULAR
		
		
		@RequestMapping(value = "/int-employee-setting", method = RequestMethod.GET)
		public String getEmployeeSetting(ModelMap model,@ModelAttribute("user") User user) {
			
			System.out.println("inside get");
			User manager = userService.getUserDetails();
			model.addAttribute("user", manager);
			return "employee/setting_employee";
		}
			@RequestMapping(value = "/int-employee-setting_success", method = RequestMethod.POST)
		public String changeEmployeeSetting(ModelMap model,
				@ModelAttribute("user") User user,BindingResult result) {
			
			System.out.println("inside post");
			User current_user= userService.getUserDetails();
			System.out.println("id is "+current_user.getCustomerID());
			ModifiedUser modifiedUser = new ModifiedUser();
			modifiedUser.setCustomerID(current_user.getCustomerID());
			modifiedUser.setAddressLine1(user.getAddressLine1());
			modifiedUser.setAddressLine2(user.getAddressLine2());
			modifiedUser.setPassword(user.getPassword());
			modifiedUser.setEmail(current_user.getEmail());
			modifiedUser.setEmployeeOverride(current_user.getEmployeeOverride());
			modifiedUser.setEnabled(current_user.getEnabled());
			modifiedUser.setFirstName(user.getFirstName());
			modifiedUser.setLastName(user.getLastName());
			modifiedUser.setMiddleName(user.getMiddleName());
			modifiedUser.setState(user.getState());
			modifiedUser.setZipCode(user.getZipCode());
			modifiedUser.setuserType(current_user.getUserType());
			modifiedUser.setPhone(user.getPhone());
			modifiedUser.setCreatedAt(current_user.getCreatedAt());
			modifiedUser.setUpdatedAt(current_user.getUpdatedAt());
			modifiedUser.setLastLoginAt(current_user.getLastLoginAt());
			modifiedUser.setStatus("pending");
			long epoch = System.currentTimeMillis()/1000;
			modifiedUser.setRequestid(epoch);
			modifiedService.addRequest(modifiedUser);
			//internalUserService.updateInternalUser(user);
			
			return "redirect:/int-employee-home";
		}
		
		
		
		@RequestMapping(value = "/int-employee-home", method = RequestMethod.GET)
		public String getInternalEmployeeHome(ModelMap model) {
			
			model.addAttribute("title", "Welcome Regular Employee");	
			return "employee/home_int_employee";
			
		}
		
		


	//**transactions
		
	
	@RequestMapping(value = "/addTransaction", method = RequestMethod.GET)
	public String addTransactionView(ModelMap model){
				
		return "employee/add_txn_search";
	}
	
	@RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
	public String SearchExternalUser(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result,RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchExternalUser(empSearch.getEmployeeID());
		if (user == null){
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"Not a valid User");
			return "redirect:/addTransaction";
		}
		else if(user.getEmployeeOverride() == 0)
		{
			redirectAttrs.addFlashAttribute(
					"failureMsg",
					"Employee not allowed by user to modify accounts for this customer");
			return "redirect:/addTransaction";
			
		}
		
		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/addCustomerTransaction";
	}
	
	@RequestMapping(value = "/addCustomerTransaction", method = RequestMethod.GET)
	public String addCustomerTransaction(ModelMap model,@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs){
		

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("user", user);
		
		return "employee/int_employee_add_transaction";
	}
	
	
		
	@RequestMapping(value = "/critical-decline", method = RequestMethod.POST)
	public String CriticalTransactionDecline( ModelMap model,
			  HttpServletRequest request) {
	    
		transactionService.declineTransaction(request.getParameter("transactionID"));
		
		return "redirect:/internalemployee-pending-critical-transaction";
			
	}
	
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String ModifyTransactions( ModelMap model,
			  HttpServletRequest request) {
		
		ModificationTransaction modificationTransaction = getModificationTransaction();
		modificationTransaction.setAmount(request.getParameter("amount"));
		modificationTransaction.setSenderAccNumber(request.getParameter("senderAccNumber"));
		modificationTransaction.setRecieverAccNumber(request.getParameter("receiverAccNumber"));
		modificationTransaction.setTransactionID(request.getParameter("transactionID"));
		model.addAttribute("modificationTransaction", modificationTransaction);
		
		
		return "employee/int_employee_modify_transaction";
	
   }
	
	@RequestMapping(value = "/critical-modify", method = RequestMethod.POST)
	public String ModifyCriticalTransactions( ModelMap model,
			  HttpServletRequest request) {
		
		ModificationTransaction modificationTransaction = getModificationTransaction();
		modificationTransaction.setAmount(request.getParameter("amount"));
		modificationTransaction.setSenderAccNumber(request.getParameter("senderAccNumber"));
		modificationTransaction.setRecieverAccNumber(request.getParameter("receiverAccNumber"));
		modificationTransaction.setTransactionID(request.getParameter("transactionID"));
		model.addAttribute("modificationTransaction", modificationTransaction);
		
		
		return "employee/int_employee_modify_critical_transaction";
	
   }





	@RequestMapping(value = "/systemLog-sys-admin", method = RequestMethod.GET)
	public String getAllLogs(ModelMap model){
		
		List<SystemLog> syslogs =  systemLogService.getAllLog();
		
		model.addAttribute("systemlogs", syslogs);
		
		
		return "employee/systemLog_sys_admin";
	
		
	}
	
	
	@RequestMapping(value = "/addTransactionSuccess", method = RequestMethod.POST)
	public String postFundTransfer(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction senderTransaction,
			BindingResult result, RedirectAttributes attr) {

		
		boolean isTransferAccountValid;
		Account account = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());
		
		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));
			
			attr.addFlashAttribute("failureMsg",
					"Could not process the transaction.Account seems to be Null or tampered.Please try again ");
			return "redirect:/addTransaction";
		}
		
		
		String receiverAccNumber = "";
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			receiverAccNumber = request.getParameter("receiverAccNumber");
			logger.info("internal transfer");
		} else {
			receiverAccNumber = request.getParameter("receiverAccNumberExternal");
			logger.info("external transfer");
		}


		Account toAccount = accountService.getAccountByNumber(receiverAccNumber); 

		
		if(toAccount != null)
		{
			isTransferAccountValid = true;
		}
		else
		{
			isTransferAccountValid = false;
			
		}
		
		logger.debug("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {
			
			BigDecimal amount = transactionService.getBigDecimal(request
					.getParameter("amount"));
			
					
			logger.debug("receiverAccNumber: " + receiverAccNumber);
			
			String isCritical = transactionService.isCritical(amount, CRITICAL_VALUE);
			String pairId = UUID.randomUUID().toString();
			
			// create the transaction object
			senderTransaction = new Transaction(
					transactionService.getUniqueTransactionID(), 
					"Fund Transfer",
					receiverAccNumber,
					request.getParameter("senderAccNumber"), 
					"completed", 
					"Debit",
					amount, 
					isCritical,
					request.getParameter("senderAccNumber"),
					pairId
				);
			
			logger.debug("Sender Transaction created: " + senderTransaction);
			
			if(amount.longValue() > 500)
			{
				senderTransaction.setIsCritical("yes");
			}
			else
			{
				senderTransaction.setIsCritical("no");
			}
			
			// Validate the model
			validator.validate(senderTransaction, result);
			logger.debug("Validated model");
			
			if (result.hasErrors()) {
				logger.debug("Validation errors: ");
				logger.debug(result);
	
				// attributes for validation failures
				attr.addFlashAttribute(
						"failureMsg",
						"Could not process your transaction");
				attr.addFlashAttribute("transaction", senderTransaction);
	
				// redirect to the credit debit view page
				return "redirect:/addTransaction";
			}
			
			logger.debug("No validation errors");
	
			// Check if Debit amount is < balance in the account
			if ( amount.compareTo(account.getBalance()) >= 0) {
				logger.debug("Debit < Balance");
				attr.addFlashAttribute(
						"failureMsg",
						"Could not process your transaction. Debit amount cannot be higher than account balance");
				return "redirect:/addTransaction";
			}
			
			
			Transaction receiverTransaction = new Transaction(
					transactionService.getUniqueTransactionID(), 
					"Fund Transfer",
					receiverAccNumber,
					request.getParameter("senderAccNumber"), 
					"completed", 
					"Credit",
					amount,
					isCritical,
					receiverAccNumber,
					pairId
				);
			
			receiverTransaction.setIsCritical("no");
			
			logger.debug("Receiver Transaction created: " + receiverTransaction);
			
			try {
				logger.debug("Trying to transfer funds");
				accountService.transferFunds(transactionService, accountService, senderTransaction, receiverTransaction, amount);
			} catch (Exception e) {
				logger.warn(e);
				attr.addFlashAttribute(
						"failureMsg",
						"Transfer unsucessful. Please try again or contact the admin.");
				return "redirect:/addTransaction";
			}
	
			attr.addFlashAttribute(
					"successMsg",
					"Transaction completed successfully. Transaction should show up on the user account now");
			
		} else {
			attr.addFlashAttribute(
					"failureMsg",
					"Transfer unsucessful. Please try again or contact the admin");
		}

		// redirect to the view page
		return "redirect:/addTransaction";
	}
	
	
	//***************************PII************************
	
	@RequestMapping(value = "/sys-admin-PII", method = RequestMethod.GET)
	public String getPII(ModelMap model){
		
		List<User> piiusers = internalUserService.getPIIUsersService();
		
		model.addAttribute("pii", piiusers);
		
		return "employee/PII_sys_admin";
				
	}
	
	
	
	

	
	
}