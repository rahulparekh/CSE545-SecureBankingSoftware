package com.sbs.group11.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.*;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.SystemLogService;
import com.sbs.group11.service.ModifiedService;
import com.sbs.group11.service.TransactionService;

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
		
		User sysadmin = internalUserService.searchInternalUserByType("admin");
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
		
		User user = internalUserService.searchInternalUser(empSearch.getEmployeeID());
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
	
	
	@RequestMapping(value = "/customer_success", params="update" ,method = RequestMethod.POST)
	public String addUserInfoPostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
		model.addAttribute("user",new User());
		internalUserService.updateInternalUser(user);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"Updated the userinformation Successfully");
		return "redirect:/manager-customer-search";
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
		internalUserService.addInternalUser(user);
		redirectAttrs.addFlashAttribute(
				"successMsg",
				"updated the userinfo Successfully");
		return "redirect:/manager-customer-search";
	}
	
	
	


	@RequestMapping(value = "/internalemployee-pendingtransaction", method = RequestMethod.GET)
	public String getPendingTransactions(ModelMap model){
		
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
				modifiedService.findModifiedUserByID(request.getParameter("modificationuserID"));
		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests_ext";
	}
	
		@RequestMapping(value = "/approve-modification-ext", method = RequestMethod.POST)
		public String approveModificationManager(ModelMap model,
				HttpServletRequest request) {
			System.out.println("See this " + request.getParameter("modifiedUserId"));
			ModifiedUser modifieduser = modifiedService.findModifiedUserByID(request.getParameter("modifiedUserId"));
			System.out.println("Customer id" + modifieduser.getCustomerID());
			modifiedService.approveRequest(modifieduser);
			return "redirect:/requests-pending-ext";
			
		}
			
		@RequestMapping(value = "/decline-modification-ext", method = RequestMethod.POST)
		public String declineModificationManager(ModelMap model,
				 HttpServletRequest request) {
			System.out.print("Inside decline transaction");
			ModifiedUser modifieduser = modifiedService.findModifiedUserByID(request.getParameter("modifiedUserId"));
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
				modifiedService.findModifiedUserByID(request.getParameter("modificationuserID"));
		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests";
	}
	
		@RequestMapping(value = "/approve-modification", method = RequestMethod.POST)
		public String approveModification(ModelMap model,
				HttpServletRequest request) {
			System.out.println("See this " + request.getParameter("modifiedUserId"));
			ModifiedUser modifieduser = modifiedService.findModifiedUserByID(request.getParameter("modifiedUserId"));
			System.out.println("Customer id" + modifieduser.getCustomerID());
			modifiedService.approveRequest(modifieduser);
			return "redirect:/requests-pending";
			
		}
			
		@RequestMapping(value = "/decline-modification", method = RequestMethod.POST)
		public String declineModification(ModelMap model,
				 HttpServletRequest request) {
			System.out.print("Inside decline transaction");
			ModifiedUser modifieduser = modifiedService.findModifiedUserByID(request.getParameter("modifiedUserId"));
			modifiedService.denyRequest(modifieduser);
			
			return "redirect:/requests-pending";
		}
		
		@RequestMapping(value = "/back-modification", method = RequestMethod.POST)
		public String backModification(ModelMap model) {
			System.out.print("HI");
			return "redirect:/requests-pending";
		}


	//**********************INTERNAL EMPLOYEE/REGULAR
		
		@RequestMapping(value = "/int-employee-home", method = RequestMethod.GET)
		public String getInternalEmployeeHome(ModelMap model) {
			
			model.addAttribute("title", "Welcome Regular Employee");	
			return "employee/home_int_employee";
			
		}
		
		@RequestMapping(value = "/int-employee-customer-search", method = RequestMethod.GET)
		public String getCustomerSearchIntEmployee(ModelMap model) {
			return "employee/int_employee_customer_search";
		}

		
		@RequestMapping(value = "/int-employee-customer-search", method = RequestMethod.POST)
		public String SearchInternalUserIntEmployee(ModelMap model,
				@ModelAttribute("empSearch") EmployeeSearch empSearch, BindingResult result,RedirectAttributes redirectAttrs) {
			
			User user = internalUserService.searchInternalUser(empSearch.getEmployeeID());
			if (user == null){
				redirectAttrs.addFlashAttribute(
						"failureMsg",
						"Not a valid User");
				return "redirect:/sysadmin-home";
			}
			redirectAttrs.addFlashAttribute("user", user);
			return "redirect:/edit-customer-int";
		}
		
		
		
		@RequestMapping(value = "/edit-customer-int", method = RequestMethod.GET)
		public String getEditUserInfoIntEmployee(ModelMap model,@ModelAttribute("user") User user) {
			Map<String,String> userTypes = new LinkedHashMap<String,String>();
			userTypes.put("customer", "customer");
			userTypes.put("merchant", "merchant");
			model.addAttribute("user", user);
			model.addAttribute("userTypes", userTypes);
			return "employee/int_employee_customers_edt";
		}
		
		
		@RequestMapping(value = "/customer_success-int", params="update" ,method = RequestMethod.POST)
		public String addUserInfoPostIntEmployee(ModelMap model,
				@ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttrs) {
			model.addAttribute("user",new User());
			internalUserService.updateInternalUser(user);
			redirectAttrs.addFlashAttribute(
					"successMsg",
					"UserInformation updated Successfully");
			return "redirect:/int-employee-customer-search";
		}
			
			
			
			
			








	//**transactions
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
		model.addAttribute("modificationTransaction", modificationTransaction);
		
		
		return "employee/int_employee_modify_transaction";
	
   }





	@RequestMapping(value = "/systemLog-sys-admin", method = RequestMethod.GET)
	public String getAllLogs(ModelMap model){
		
		List<SystemLog> syslogs =  systemLogService.getAllLog();
		
		model.addAttribute("systemlogs", syslogs);
		
		
		return "employee/systemLog_sys_admin";
	
		
	}
}