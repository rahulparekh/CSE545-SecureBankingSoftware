package com.sbs.group11.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
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
import com.sbs.group11.service.BCryptHashService;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.SystemLogService;
import com.sbs.group11.service.ModifiedService;
import com.sbs.group11.service.TransactionService;
import com.sbs.group11.service.UserService;

@Controller
public class  InternalUserController{
	
	@Autowired
	private BCryptHashService hashService;
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
	public TransactionSearch getTransactionObject() {
		return new TransactionSearch();
	}

	@ModelAttribute("modifiedUser")
	public ModifiedUser getModifiedUser() {
		return new ModifiedUser();
	}

	@ModelAttribute("ModificationTransaction")
	public ModificationTransaction getModificationTransaction() {
		return new ModificationTransaction();
	}

	@ModelAttribute("changepassword")
	public ChangePassword getChangePasswordObject() {
		return new ChangePassword();		
	}

	// **************************PII starts************************************
	@RequestMapping(value = "/government/government-home", method = RequestMethod.GET)
	public String getGovernemntHomePage(ModelMap model) {

		model.addAttribute("title", "Welcome Government authority");
		return "employee/home_government";
	}

	@RequestMapping(value = "/government/government-home", method = RequestMethod.POST)
	public String SearchInternalUserbyGovAuthority(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch,
			BindingResult result, RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchExternalUser(empSearch
				.getEmployeeID());
		if (user == null) {
			redirectAttrs.addFlashAttribute("failureMsg", "Not a valid User");
			return "redirect:/government/government-home";
		}

		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/government/viewcustomer-government";
	}

	@RequestMapping(value = "/government/viewcustomer-government", method = RequestMethod.GET)
	public String getGovermentViewCustomerPage(ModelMap model,
			@ModelAttribute("user") User user) {
		model.addAttribute("PIIUser", user);
		model.addAttribute("title", "Welcome Government authority");
		return "employee/approve_govt";
	}

	@RequestMapping(value = "/government/approve-modification-govt", method = RequestMethod.POST)
	public String approveModificationGOVT(ModelMap model,
			HttpServletRequest request) {
		User user = internalUserService.findUserByID(request
				.getParameter("PIImodifiedUserID"));

		internalUserService.approvePIIUserModification(user);
		return "redirect:/government/government-home";

	}

	@RequestMapping(value = "/government/decline-govt", method = RequestMethod.POST)
	public String DeclineModificationGOVT(ModelMap model,
			HttpServletRequest request) {

		User user = internalUserService.findUserByID(request
				.getParameter("PIImodifiedUserID"));

		internalUserService.declinePIIUserModification(user);
		return "redirect:/government/government-home";

	}

	@RequestMapping(value = "/government/back-modification-govt", method = RequestMethod.POST)
	public String BackModificationGOVT(ModelMap model,
			HttpServletRequest request) {

		return "redirect:/government/government-home";

	}

	// **************************PII ends************************************

	// ///************SYS ADMIN STARTS*****************//////////////////

	@RequestMapping(value = "admin/sysadmin-home", method = RequestMethod.GET)
	public String getSysAdminHome(ModelMap model) {

		model.addAttribute("title", "Welcome System Admin:");
		User user = userService.getUserDetails();
		model.addAttribute("user", user);
		return "employee/home_sys_admin";

	}

	@RequestMapping(value = "/admin/sysadmin-home", method = RequestMethod.POST)
	public String SearchInternalUser(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch,
			BindingResult result, RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchInternalUser(empSearch
				.getEmployeeID());
		if (user == null) {
			redirectAttrs.addFlashAttribute("failureMsg",
					"Not a valid Employee");
			return "redirect:/admin/sysadmin-home";
		}

		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/admin/edit-employee";
	}

	@RequestMapping(value = "/admin/edit-employee", method = RequestMethod.GET)
	public String getEditUserInfo(ModelMap model,
			@ModelAttribute("user") User user) {
		Map<String, String> userTypes = new LinkedHashMap<String, String>();

		userTypes.put("Regular", "Regular");
		userTypes.put("Manager", "Manager");
		model.addAttribute("user", user);
		model.addAttribute("email", user.getEmail());
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_employees_edt";
	}

	@RequestMapping(value = "/admin/edit-employee_success", method = RequestMethod.POST)
	public String AddInternalUser(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result) {
		internalUserService.updateInternalUser(user);
		return "redirect:/admin/sysadmin-home";
	}

	@RequestMapping(value = "/admin/manage-employee", method = RequestMethod.GET)
	public String addUserInfo(ModelMap model) {
		Map<String, String> userTypes = new LinkedHashMap<String, String>();
		userTypes.put("Regular", "Regular");
		userTypes.put("Manager", "Manager");
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_employees";
	}

	@RequestMapping(value = "/admin/manage-employee_success", method = RequestMethod.POST)
	public String AddExternalUserpost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (internalUserService.findUserByEmail(user.getEmail()) != null) {
			redirectAttrs.addFlashAttribute("failureMsg",
					"Employee Already Exists with the same Email Address");
			return "redirect:/admin/manage-employee";
		}
		
		if (request.getParameter("secQuestion1") == null 
				|| request.getParameter("secQuestion1").isEmpty()
				|| request.getParameter("secQuestion2") == null 
				|| request.getParameter("secQuestion2").isEmpty()
				|| request.getParameter("secQuestion3") == null 
				|| request.getParameter("secQuestion3").isEmpty()
				|| request.getParameter("answer1") == null 
				|| request.getParameter("answer1").isEmpty()
				|| request.getParameter("answer2") == null 
				|| request.getParameter("answer2").isEmpty()
				|| request.getParameter("answer3") == null 
				|| request.getParameter("answer3").isEmpty()) {
			
			redirectAttrs.addFlashAttribute("failureMsg",
					"Security Questions and Answers are required");
			return "redirect:/manager/manage-customer"; 
		}

		Set<SecurityQuestion> secQuestions = new HashSet<SecurityQuestion>();

		SecurityQuestion question1 = new SecurityQuestion();
		question1.setQuestion(request.getParameter("secQuestion1"));
		question1.setAnswer(request.getParameter("answer1"));
		question1.setCreatedAt(LocalDateTime.now());
		question1.setUpdatedAt(LocalDateTime.now());

		SecurityQuestion question2 = new SecurityQuestion();
		question2.setQuestion(request.getParameter("secQuestion2"));
		question2.setAnswer(request.getParameter("answer2"));
		question2.setCreatedAt(LocalDateTime.now());
		question2.setUpdatedAt(LocalDateTime.now());

		SecurityQuestion question3 = new SecurityQuestion();
		question3.setQuestion(request.getParameter("secQuestion3"));
		question3.setAnswer(request.getParameter("answer3"));
		question3.setCreatedAt(LocalDateTime.now());
		question3.setUpdatedAt(LocalDateTime.now());

		secQuestions.add(question1);
		secQuestions.add(question2);
		secQuestions.add(question3);
		user.setSecurityQuestions(secQuestions);
		
		String customerID;
		while (true) {
			customerID = "" + internalUserService.generateRandomNumberOfLength(11);
			if (internalUserService.findUserByID(customerID) == null) {
				break;
			}
		}
		Set<SecurityQuestion> secquestions = user.getSecurityQuestions();
		for (SecurityQuestion question : secquestions) {

			question.setUser(user);

		}
		user.setCustomerID(customerID);
		user.setCreatedAt(LocalDateTime.now());
		user.setLastLoginAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setEnabled(1);
		
		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			redirectAttrs.addFlashAttribute(
					"org.springframework.validation.BindingResult.user",
					result);
			redirectAttrs.addFlashAttribute("user", user);

			redirectAttrs.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the manager view
			return "redirect:/admin/manage-employee";
		}
		
		internalUserService.addInternalUser(user);
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),
				user.getFirstName(), user.getUserType(), "The user "
						+ user.getFirstName() + " is created");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute("successMsg",
				"Employee Added Successfully");
		return "redirect:/admin/sysadmin-home";
	}

	@RequestMapping(value = "/admin/employee_success", params = "update", method = RequestMethod.POST)
	public String addUserInfoPost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {
		model.addAttribute("user", user);
		model.addAttribute("user", new User());
		internalUserService.updateInternalUser(user);
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),
				user.getFirstName(), user.getUserType(), "The user "
						+ user.getFirstName() + " info is updated");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute("successMsg",
				"Employee Information updated Successfully");
		return "redirect:/admin/sysadmin-home";
	}

	@RequestMapping(value = "/admin/employee_success", params = "delete", method = RequestMethod.POST)
	public String deleteUserInfoPost(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {
		model.addAttribute("user", user);
		model.addAttribute("user", new User());
		internalUserService.deleteInternalUserById(user.getCustomerID());
		SystemLog systemLog = new SystemLog(new DateTime().toLocalDateTime(),
				user.getFirstName(), user.getUserType(), "The user "
						+ user.getFirstName() + " is deleted");
		systemLogService.addLog(systemLog);
		redirectAttrs.addFlashAttribute("successMsg",
				"Deleted the Employee Successfully");
		return "redirect:/admin/sysadmin-home";
	}

	@RequestMapping(value = "/admin/sysadmin-setting", method = RequestMethod.GET)
	public String getAdminSetting(ModelMap model) {
		User sysadmin = userService.getUserDetails();
		model.addAttribute("sysadmin", sysadmin);
		model.addAttribute("email", sysadmin.getEmail());
		return "employee/setting_sys_admin";
	}

	@RequestMapping(value = "/admin/sysadmin-setting_success", method = RequestMethod.POST)
	public String changeAdminSetting(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttrs) {

		user.setUserType("admin");
		
		User current_user = internalUserService.findUserByID(user.getCustomerID());
		if(current_user != null)
		{
			user.setEmail(current_user.getEmail());
			user.setPassword(current_user.getPassword());
			user.setCreatedAt(current_user.getCreatedAt());
			user.setUpdatedAt(new DateTime().toLocalDateTime());
			user.setLastLoginAt(new DateTime().toLocalDateTime());
		} else {
			
			redirectAttrs.addFlashAttribute("failureMsg",
					"User not found.");

			return "redirect:/admin/sysadmin-setting";
			
		}
		
		validator.validate(user, result);
		logger.debug("Validated model");
		if (result.hasErrors()) {
			logger.debug("Validation errors: ");
			logger.debug(result);

			// attributes for validation failures
			redirectAttrs.addFlashAttribute("failureMsg",
					"Could not process your request. You have errors");
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			redirectAttrs.addFlashAttribute("user", user);

			return "redirect:/admin/sysadmin-setting";
		}
		
		internalUserService.updateInternalUser(user);
		
		redirectAttrs.addFlashAttribute("successMsg",
				"Settings updated successfully.");
		return "redirect:/admin/sysadmin-setting";
	}

	// ///***************SYS ADMIN ENDS*****************************

	// ********************************MANAGER**************************

	@RequestMapping(value = "/manager/manager-home", method = RequestMethod.GET)
	public String getManagerHome(ModelMap model) {

		model.addAttribute("title", "Welcome Manager:");
		User user = userService.getUserDetails();
		model.addAttribute("user", user);
		return "employee/home_manager";

	}

	@RequestMapping(value = "/manager/get-list-of-accounts-for-manager", method = RequestMethod.POST)
	public String getAccountsforaCustomerManager(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		User user = userService.getUserbyCustomerID(request
				.getParameter("customerID"));

		if (user == null) {

			attr.addFlashAttribute("failureMsg",
					"The CustomerID entered is either Invalid or does exist");
			return "redirect:/manager/manager-home";

		}

		if (user.getEmployeeOverride() == 1) {

			List<Account> accounts = accountService
					.getAccountsByCustomerID(request.getParameter("customerID"));
			model.addAttribute("accounts", accounts);
			return "employee/home_manager";

		} else {

			attr.addFlashAttribute("failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			return "redirect:/manager/manager-home";

		}

	}

	@RequestMapping(value = "/manager/getTransactionforAccount-manager", method = RequestMethod.POST)
	public String getTransactionsforAccountsManager(ModelMap model,
			HttpServletRequest request) {

		List<Transaction> transactions = transactionService
				.getTransactionsForAccountNumber(request
						.getParameter("accNumber"));
		model.addAttribute("transactions", transactions);
		return "employee/manager_show_transactions";
	}

	@RequestMapping(value = "/manager/get-transaction-for-transactionID-manager", method = RequestMethod.POST)
	public String getTransactionforTransactionIDManager(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		Transaction transaction = transactionService.getTransaction(request
				.getParameter("transactionID"));

		if (transaction == null) {

			attr.addFlashAttribute("failureMsg",
					"TransactionID Invalid or Not Found");
			return "redirect:/manager/manager-home";
		}

		Account account = accountService.getAccountByNumber(transaction
				.getSenderAccNumber());
		User user = userService.getUserbyCustomerID(account.getUser()
				.getCustomerID());

		if (user.getEmployeeOverride() == 1) {
			model.addAttribute("transaction", transaction);
			return "employee/manager_show_single_transaction";
		} else {
			// Flash attribute
			attr.addFlashAttribute("failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			return "redirect:/manager/manager-home";
		}

	}

	@RequestMapping(value = "/manager/manager-customer-search", method = RequestMethod.GET)
	public String getCustomerSearch(ModelMap model) {
		return "employee/manager_customer_search";
	}

	@RequestMapping(value = "/manager/manager-customer-search", method = RequestMethod.POST)
	public String SearchInternalUserManager(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch,
			BindingResult result, RedirectAttributes redirectAttrs) {

		User user = internalUserService.searchExternalUser(empSearch
				.getEmployeeID());
		if (user == null) {
			redirectAttrs.addFlashAttribute("failureMsg", "Not a valid User");
			return "redirect:/manager/manager-customer-search";
		}

		if (user.getEmployeeOverride() == 1) {

			redirectAttrs.addFlashAttribute("user", user);
			return "redirect:/manager/edit-customer";

		} else {
			redirectAttrs.addFlashAttribute("failureMsg",
					"The user has not provided access to view");
			return "redirect:/manager/manager-customer-search";

		}
	}

	@RequestMapping(value = "/manager/edit-customer", method = RequestMethod.GET)
	public String getEditUserInfoManager(ModelMap model,
			@ModelAttribute("user") User user) {
		Map<String, String> userTypes = new LinkedHashMap<String, String>();

		userTypes.put("Customer", "Customer");
		userTypes.put("Merchant", "Merchant");
		model.addAttribute("user", user);
		model.addAttribute("email", user.getEmail());
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_customers_edt";
	}

	@RequestMapping(value = "/manager/customer_success", params = "update", method = RequestMethod.POST)
	public String addUserInfoPostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {
		model.addAttribute("user", new User());
		internalUserService.updateInternalUser(user);
		redirectAttrs.addFlashAttribute("successMsg",
				"Updated the user Information Successfully");
		return "redirect:/manager/manager-customer-search";
	}

	@RequestMapping(value = "/manager/customer_success", params = "delete", method = RequestMethod.POST)
	public String deleteUserInfoPostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {

		internalUserService.deleteInternalUserById(user.getCustomerID());
		model.addAttribute("user", new User());
		redirectAttrs.addFlashAttribute("successMsg",
				"Deleted the user Successfully");
		return "redirect:/manager/manager-customer-search";
	}

	@RequestMapping(value = "/manager/manage-customer", method = RequestMethod.GET)
	public String addUserInfoManager(ModelMap model) {
		Map<String, String> userTypes = new LinkedHashMap<String, String>();
		
		userTypes.put("Customer", "Customer");
		userTypes.put("Merchant", "Merchant");
		
		model.addAttribute("userTypes", userTypes);
		
		return "employee/manage_customers";
	}

	@RequestMapping(value = "/manager/manage-customer_success", method = RequestMethod.POST)
	public String AddExternalUserpostManager(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		model.addAttribute("user", new User());
		if (internalUserService.findUserByEmail(user.getEmail()) != null) {
			redirectAttrs.addFlashAttribute("failureMsg",
					"User Already Exists with the same Email Address");
			return "redirect:/manager/manage-customer";
		}
		
		if (request.getParameter("secQuestion1") == null 
				|| request.getParameter("secQuestion1").isEmpty()
				|| request.getParameter("secQuestion2") == null 
				|| request.getParameter("secQuestion2").isEmpty()
				|| request.getParameter("secQuestion3") == null 
				|| request.getParameter("secQuestion3").isEmpty()
				|| request.getParameter("answer1") == null 
				|| request.getParameter("answer1").isEmpty()
				|| request.getParameter("answer2") == null 
				|| request.getParameter("answer2").isEmpty()
				|| request.getParameter("answer3") == null 
				|| request.getParameter("answer3").isEmpty()) {
			
			redirectAttrs.addFlashAttribute("failureMsg",
					"Security Questions and Answers are required");
			return "redirect:/manager/manage-customer"; 
		}

		Set<SecurityQuestion> secQuestions = new HashSet<SecurityQuestion>();

		SecurityQuestion question1 = new SecurityQuestion();
		question1.setQuestion(request.getParameter("secQuestion1"));
		question1.setAnswer(request.getParameter("answer1"));
		question1.setCreatedAt(LocalDateTime.now());
		question1.setUpdatedAt(LocalDateTime.now());

		SecurityQuestion question2 = new SecurityQuestion();
		question2.setQuestion(request.getParameter("secQuestion2"));
		question2.setAnswer(request.getParameter("answer2"));
		question2.setCreatedAt(LocalDateTime.now());
		question2.setUpdatedAt(LocalDateTime.now());

		SecurityQuestion question3 = new SecurityQuestion();
		question3.setQuestion(request.getParameter("secQuestion3"));
		question3.setAnswer(request.getParameter("answer3"));
		question3.setCreatedAt(LocalDateTime.now());
		question3.setUpdatedAt(LocalDateTime.now());

		secQuestions.add(question1);
		secQuestions.add(question2);
		secQuestions.add(question3);
		user.setSecurityQuestions(secQuestions);
		
		String customerID;
		while (true) {
			customerID = "" + internalUserService.generateRandomNumberOfLength(11);
			if (internalUserService.findUserByID(customerID) == null) {
				break;
			}
		}
		Set<SecurityQuestion> secquestions = user.getSecurityQuestions();
		for (SecurityQuestion question : secquestions) {

			question.setUser(user);

		}
		user.setCustomerID(customerID);
		user.setCreatedAt(LocalDateTime.now());
		user.setLastLoginAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setEnabled(1);
		
		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			redirectAttrs.addFlashAttribute(
					"org.springframework.validation.BindingResult.user",
					result);
			redirectAttrs.addFlashAttribute("user", user);

			redirectAttrs.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the manager view
			return "redirect:/manager/manage-customer";
		}

		internalUserService.addInternalUser(user);
		redirectAttrs.addFlashAttribute("successMsg",
				"Added the User Successfully");
		return "redirect:/manager/manager-customer-search";
	}

	@RequestMapping(value = "/manager/critical-approve", method = RequestMethod.POST)
	public String CriticalTransactionApprove(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		if (request.getParameter("transactionID") == null) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
			return "redirect:/manager/internalemployee-pending-critical-transaction";
		}

		Transaction transaction = transactionService.getTransaction(request
				.getParameter("transactionID"));
		boolean result = transactionService.approveTransaction(transaction);

		logger.info(result);

		if (!result) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
		}

		return "redirect:/manager/internalemployee-pending-critical-transaction";

	}

	@RequestMapping(value = "/manager/critical-decline", method = RequestMethod.POST)
	public String CriticalTransactionDecline(ModelMap model,
			HttpServletRequest request) {

		transactionService.declineTransaction(request
				.getParameter("transactionID"));

		return "redirect:/manager/internalemployee-pending-critical-transaction";

	}

	@RequestMapping(value = "/manager/modificiation-critical-transction-approve", method = RequestMethod.POST)
	public String ModificationCriticalTransactionApprove(
			ModelMap model,
			@ModelAttribute("modificationTransaction") ModificationTransaction modificationTransaction,
			HttpServletRequest request, RedirectAttributes attr) {

		try {

			Transaction transaction = transactionService.getTransaction(request
					.getParameter("modifytransactionID"));

			if (modificationTransaction.getAmount() != null) {
				BigDecimal amount = new BigDecimal(
						modificationTransaction.getAmount());
				BigDecimal zeroamount = new BigDecimal(0);
				if ((amount.compareTo(zeroamount) == 0)
						|| (amount.compareTo(zeroamount) == -1)) {

					attr.addFlashAttribute("failureMsg",
							"Amount should be greater than zero");
					return "redirect:/manager/internalemployee-pending-critical-transaction";

				}

				transaction.setAmount(amount);
				transaction.setSenderAccNumber(modificationTransaction
						.getSenderAccNumber());
				transaction.setReceiverAccNumber(modificationTransaction
						.getRecieverAccNumber());

				Account accountSender = accountService
						.getAccountByNumber(modificationTransaction
								.getSenderAccNumber());

				if (accountSender == null) {

					attr.addFlashAttribute("failureMsg",
							"Invalid AccountNumber");
					return "redirect:/manager/internalemployee-pending-critical-transaction";
				}

				Account accountReciever = accountService
						.getAccountByNumber(modificationTransaction
								.getRecieverAccNumber());

				if (accountReciever == null) {

					attr.addFlashAttribute("failureMsg",
							"Invalid AccountNumber");
					return "redirect:/manager/internalemployee-pending-critical-transaction";

				}

				User user = userService.getUserbyCustomerID(accountSender
						.getUser().getCustomerID());
				if (user.getEmployeeOverride() == 1) {

					boolean result = transactionService
							.approveTransactionafterModification(transaction);

					if (!result) {
						attr.addFlashAttribute(
								"failureMsg",
								"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
					} else {

						attr.addFlashAttribute("sucessMsg",
								"Transaction Approved Sucessfully with modifications");
					}
				} else {

					attr.addFlashAttribute("failureMsg",
							"You may not have the neccessary permissions to approve this transaction");
				}

			} else {

				attr.addFlashAttribute("failureMsg",
						"Please put to a valid amount to process this transaction");
				model.addAttribute("modificationTransaction",
						modificationTransaction);
				return "redirect:/manager/internalemployee-pending-critical-transaction";
			}

			return "redirect:/manager/internalemployee-pending-critical-transaction";
		} catch (Exception e) {

			attr.addFlashAttribute("failureMsg",
					"Please put to a valid amount to process this transaction");
			model.addAttribute("modificationTransaction",
					modificationTransaction);
			return "redirect:/manager/internalemployee-pending-critical-transaction";
		}
	}

	@RequestMapping(value = "/manager/critical-modify", method = RequestMethod.POST)
	public String ModifyCriticalTransactions(ModelMap model,
			HttpServletRequest request) {

		ModificationTransaction modificationTransaction = getModificationTransaction();
		modificationTransaction.setAmount(request.getParameter("amount"));
		modificationTransaction.setSenderAccNumber(request
				.getParameter("senderAccNumber"));
		modificationTransaction.setRecieverAccNumber(request
				.getParameter("receiverAccNumber"));
		modificationTransaction.setTransactionID(request
				.getParameter("transactionID"));
		model.addAttribute("modificationTransaction", modificationTransaction);
		return "employee/int_employee_modify_critical_transaction";

	}

	@RequestMapping(value = "/manager/manager-setting", method = RequestMethod.GET)
	public String getManagerSetting(ModelMap model) {

		User manager = userService.getUserDetails();
		model.addAttribute("manager", manager);
		model.addAttribute("email", manager.getEmail());
		return "employee/setting_manager";
	}

	@RequestMapping(value = "/manager/manager-setting_success", method = RequestMethod.POST)
	public String changeManagerSetting(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes attr) {

		User currentUser = userService.getUserDetails();

		// Validate fields
		user.setCustomerID(currentUser.getCustomerID());
		user.setEmail(currentUser.getEmail());
		user.setEmployeeOverride(currentUser.getEmployeeOverride());
		user.setEnabled(currentUser.getEnabled());
		user.setUserType(currentUser.getUserType());
		user.setCreatedAt(new DateTime().toLocalDateTime());
		user.setLastLoginAt(currentUser.getLastLoginAt());
		user.setPassword(currentUser.getPassword());
		user.setUpdatedAt(new DateTime().toLocalDateTime());

		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);

			attr.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the credit debit view page
			return "redirect:/manager/manager-setting";
		}

		ModifiedUser modifiedUser = new ModifiedUser();
		modifiedUser.setCustomerID(currentUser.getCustomerID());
		modifiedUser.setAddressLine1(user.getAddressLine1());
		modifiedUser.setAddressLine2(user.getAddressLine2());
		modifiedUser.setPassword(currentUser.getPassword());
		modifiedUser.setEmail(currentUser.getEmail());
		modifiedUser.setEmployeeOverride(currentUser.getEmployeeOverride());
		modifiedUser.setEnabled(currentUser.getEnabled());
		modifiedUser.setFirstName(user.getFirstName());
		modifiedUser.setLastName(user.getLastName());
		modifiedUser.setMiddleName(user.getMiddleName());
		modifiedUser.setState(user.getState());
		modifiedUser.setZipCode(user.getZipCode());
		modifiedUser.setUserType(currentUser.getUserType());
		modifiedUser.setPhone(user.getPhone());
		modifiedUser.setCreatedAt(currentUser.getCreatedAt());
		modifiedUser.setUpdatedAt(new DateTime().toLocalDateTime());
		modifiedUser.setLastLoginAt(currentUser.getLastLoginAt());
		modifiedUser.setStatus("pending");
		modifiedUser.setEmployeeOverride(user.getEmployeeOverride());
		long epoch = System.currentTimeMillis() / 1000;
		modifiedUser.setRequestid(epoch);
		modifiedService.addRequest(modifiedUser);

		model.addAttribute("title", "Your Settings!");

		attr.addFlashAttribute("successMsg",
				"Your request was successful. Please wait for bank approval.");


		return "redirect:/manager/manager-home";
	}

	// ///********************************INTERNAL EMPLOYEE********************

	@RequestMapping(value = "/regular/int-employee-home", method = RequestMethod.GET)
	public String getInternalEmployeeHome(ModelMap model) {		
		model.addAttribute("title", "Welcome Regular Employee");
		User user = userService.getUserDetails();
		model.addAttribute("user", user);
		return "employee/home_int_employee";

	}

	@RequestMapping(value = "/regular/get-list-of-accounts-for", method = RequestMethod.POST)
	public String getAccountsforaCustomer(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		// Overrrrride check
		User user = userService.getUserbyCustomerID(request
				.getParameter("customerID"));

		if (user == null) {

			attr.addFlashAttribute("failureMsg",
					"The CustomerID entered is either Invalid or does exist");
			return "redirect:/regular/int-employee-home";

		}

		if (user.getEmployeeOverride() == 1) {

			List<Account> accounts = accountService
					.getAccountsByCustomerID(request.getParameter("customerID"));
			model.addAttribute("accounts", accounts);
			return "employee/home_int_employee";

		} else {
			// Flash Attribute
			attr.addFlashAttribute("failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			return "redirect:/regular/int-employee-home";

		}

	}

	@RequestMapping(value = "/regular/getTransactionforAccount", method = RequestMethod.POST)
	public String getTransactionsforAccounts(ModelMap model,
			HttpServletRequest request) {

		List<Transaction> transactions = transactionService
				.getTransactionsForAccountNumber(request
						.getParameter("accNumber"));
		model.addAttribute("transactions", transactions);
		return "employee/int_employee_show_transactions";
	}

	@RequestMapping(value = "/regular/get-transaction-for-transactionID", method = RequestMethod.POST)
	public String getTransactionforTransactionID(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		// Overrrrrrride Check
		Transaction transaction = transactionService.getTransaction(request
				.getParameter("transactionID"));

		if (transaction == null) {

			attr.addFlashAttribute("failureMsg",
					"TransactionID Invalid or Not Found");
			return "redirect:/regular/int-employee-home";
		}

		Account account = accountService.getAccountByNumber(transaction
				.getSenderAccNumber());
		User user = userService.getUserbyCustomerID(account.getUser()
				.getCustomerID());

		if (user.getEmployeeOverride() == 1) {
			model.addAttribute("transaction", transaction);
			return "employee/int_employee_show_single_transaction";
		} else {
			// Flash attribute
			attr.addFlashAttribute("failureMsg",
					"Could not process the request. You may not have the necessary permissions !!");
			return "redirect:/regular/int-employee-home";
		}

	}

	@RequestMapping(value = "/regular/int-employee-customer-search", method = RequestMethod.GET)
	public String getCustomerSearchIntEmployee(ModelMap model) {
		return "employee/int_employee_customer_search";
	}

	@RequestMapping(value = "/regular/int-employee-customer-search", method = RequestMethod.POST)
	public String SearchInternalUserIntEmployee(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch,
			BindingResult result, RedirectAttributes redirectAttrs) {

		User user = internalUserService.searchExternalUser(empSearch
				.getEmployeeID());
		if (user == null) {
			redirectAttrs.addFlashAttribute("failureMsg", "Not a valid User");
			return "redirect:/regular/int-employee-customer-search";
		}
		if (user.getEmployeeOverride() == 1) {
			redirectAttrs.addFlashAttribute("user", user);
			return "redirect:/regular/edit-customer-int";
		} else {
			redirectAttrs.addFlashAttribute("failureMsg",
					"The user has not provided access to view");
			return "redirect:/regular/int-employee-customer-search";
		}
	}

	@RequestMapping(value = "/regular/edit-customer-int", method = RequestMethod.GET)
	public String getEditUserInfoIntEmployee(ModelMap model) {
		Map<String, String> userTypes = new LinkedHashMap<String, String>();
		userTypes.put("Customer", "Customer");
		userTypes.put("Merchant", "Merchant");
		model.addAttribute("user", (User) model.get("user"));
		model.addAttribute("userTypes", userTypes);
		return "employee/int_employee_customers_edt";
	}

	@RequestMapping(value = "/regular/customer_success-int", params = "update", method = RequestMethod.POST)
	public String addUserInfoPostIntEmployee(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {
		
		User current_user = internalUserService.findUserByID(user.getCustomerID());
		if(current_user != null)
		{
			user.setEmail(current_user.getEmail());
			user.setPassword(current_user.getPassword());
			user.setCreatedAt(current_user.getCreatedAt());
			user.setUpdatedAt(new DateTime().toLocalDateTime());
			user.setLastLoginAt(new DateTime().toLocalDateTime());
		} else {
			
			redirectAttrs.addFlashAttribute("failureMsg",
					"User not found.");

			// redirect to the credit debit view page
			return "redirect:/regular/edit-customer-int";
			
		}
		
		validator.validate(user, result);
		logger.debug("Validated model");
		if (result.hasErrors()) {
			logger.debug("Validation errors: ");
			logger.debug(result);

			// attributes for validation failures
			redirectAttrs.addFlashAttribute("failureMsg",
					"Could not process your request. You have errors");
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			redirectAttrs.addFlashAttribute("user", user);

			// redirect to the credit debit view page
			return "redirect:/regular/edit-customer-int";
		}
		
		internalUserService.updateInternalUser(user);
		redirectAttrs.addFlashAttribute("successMsg",
				"UserInformation updated Successfully");
		return "redirect:/regular/int-employee-customer-search";
	}

	@RequestMapping(value = "/regular/addTransaction", method = RequestMethod.GET)
	public String addTransactionView(ModelMap model) {

		return "employee/add_txn_search";
	}

	@RequestMapping(value = "/regular/addTransaction", method = RequestMethod.POST)
	public String SearchExternalUser(ModelMap model,
			@ModelAttribute("empSearch") EmployeeSearch empSearch,
			BindingResult result, RedirectAttributes redirectAttrs) {
		User user = internalUserService.searchExternalUser(empSearch
				.getEmployeeID());
		if (user == null) {
			redirectAttrs.addFlashAttribute("failureMsg", "Not a valid User");
			return "redirect:/regular/addTransaction";
		} else if (user.getEmployeeOverride() == 0) {
			redirectAttrs
					.addFlashAttribute("failureMsg",
							"Employee not allowed by user to modify accounts for this customer");
			return "redirect:/regular/addTransaction";

		}

		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/regular/addCustomerTransaction";
	}

	@RequestMapping(value = "/regular/addCustomerTransaction", method = RequestMethod.GET)
	public String addCustomerTransaction(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());

		model.addAttribute("accounts", accounts);
		model.addAttribute("user", user);

		return "employee/int_employee_add_transaction";
	}

	@RequestMapping(value = "/regular/modify", method = RequestMethod.POST)
	public String ModifyTransactions(ModelMap model, HttpServletRequest request) {

		ModificationTransaction modificationTransaction = getModificationTransaction();
		modificationTransaction.setAmount(request.getParameter("amount"));
		modificationTransaction.setSenderAccNumber(request
				.getParameter("senderAccNumber"));
		modificationTransaction.setRecieverAccNumber(request
				.getParameter("receiverAccNumber"));
		modificationTransaction.setTransactionID(request
				.getParameter("transactionID"));
		model.addAttribute("modificationTransaction", modificationTransaction);

		return "employee/int_employee_modify_transaction";

	}

	@RequestMapping(value = "/regular/addTransactionSuccess", method = RequestMethod.POST)
	public String postFundTransfer(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction senderTransaction,
			BindingResult result, RedirectAttributes attr) {

		boolean isTransferAccountValid;
		Account account = accountService.getAccountByNumber(senderTransaction
				.getSenderAccNumber());

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));

			attr.addFlashAttribute(
					"failureMsg",
					"Could not process the transaction.Account seems to be Null or tampered.Please try again ");
			return "redirect:/regular/addTransaction";
		}

		String receiverAccNumber = "";
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			receiverAccNumber = request.getParameter("receiverAccNumber");
			logger.info("internal transfer");
		} else {
			receiverAccNumber = request
					.getParameter("receiverAccNumberExternal");
			logger.info("external transfer");
		}

		if (receiverAccNumber.trim().equalsIgnoreCase(
				request.getParameter("senderAccNumber").trim())) {

			attr.addFlashAttribute(
					"failureMsg",
					"Could not process transaction. Transferring to the same account is not allowed.");
			return "redirect:/home/fund-transfer";
		}

		Account toAccount = accountService
				.getAccountByNumber(receiverAccNumber);

		if (toAccount != null) {
			isTransferAccountValid = true;
		} else {
			isTransferAccountValid = false;
		}

		logger.debug("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {

			BigDecimal amount = transactionService.getBigDecimal(request
					.getParameter("amount"));

			logger.debug("receiverAccNumber: " + receiverAccNumber);

			String isCritical = transactionService.isCritical(amount,
					CRITICAL_VALUE);
			String pairId = UUID.randomUUID().toString();

			// create the transaction object
			senderTransaction = new Transaction(
					transactionService.getUniqueTransactionID(),
					"Fund Transfer", receiverAccNumber,
					request.getParameter("senderAccNumber"), "approved",
					"Debit", amount, isCritical,
					request.getParameter("senderAccNumber"), pairId);

			logger.debug("Sender Transaction created: " + senderTransaction);

			// Validate the model
			validator.validate(senderTransaction, result);
			logger.debug("Validated model");

			if (result.hasErrors()) {
				logger.debug("Validation errors: ");
				logger.debug(result);

				// attributes for validation failures
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction");
				attr.addFlashAttribute("transaction", senderTransaction);

				// redirect to the credit debit view page
				return "redirect:/regular/addTransaction";
			}

			logger.debug("No validation errors");

			// Check if Debit amount is < balance in the account
			if (amount.compareTo(account.getBalance()) >= 0) {
				logger.debug("Debit < Balance");
				attr.addFlashAttribute(
						"failureMsg",
						"Could not process your transaction. Debit amount cannot be higher than account balance");
				return "redirect:/regular/addTransaction";
			}

			Transaction receiverTransaction = new Transaction(
					transactionService.getUniqueTransactionID(),
					"Fund Transfer", receiverAccNumber,
					request.getParameter("senderAccNumber"), "approved",
					"Credit", amount, isCritical, receiverAccNumber, pairId);

			receiverTransaction.setIsCritical("no");

			logger.debug("Receiver Transaction created: " + receiverTransaction);

			try {
				logger.debug("Trying to transfer funds");
				accountService.transferFunds(transactionService,
						accountService, senderTransaction, receiverTransaction,
						amount);
			} catch (Exception e) {
				logger.warn(e);
				attr.addFlashAttribute("failureMsg",
						"Transfer unsucessful. Please try again or contact the admin.");
				return "redirect:/regular/addTransaction";
			}

			attr.addFlashAttribute(
					"successMsg",
					"Transaction completed successfully. Transaction should show up on the user account now");

		} else {
			attr.addFlashAttribute("failureMsg",
					"Transfer unsucessful. Please try again or contact the admin");
		}

		// redirect to the view page
		return "redirect:/regular/addTransaction";
	}

	@RequestMapping(value = "/regular/internalemployee-pendingtransaction", method = RequestMethod.GET)
	public String getPendingTransactions(ModelMap model) {

		System.out.println("Inside pending");
		User user = userService.getUserDetails();
		List<Transaction> pendingTransaction = transactionService
				.getPendingTransactions();

		model.addAttribute("pendingTransaction", pendingTransaction);

		return "employee/int_employee_pending_transaction";

	}

	@RequestMapping(value = "/regular/approve", method = RequestMethod.POST)
	public String TransactionApprove(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		if (request.getParameter("transactionID") == null) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
			return "redirect:/regular/internalemployee-pendingtransaction";
		}

		Transaction transaction = transactionService.getTransaction(request
				.getParameter("transactionID"));
		boolean result = transactionService.approveTransaction(transaction);

		logger.debug(result);
		if (!result) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
		}

		return "redirect:/regular/internalemployee-pendingtransaction";

	}

	@RequestMapping(value = "/regular/decline", method = RequestMethod.POST)
	public String TransactionDecline(ModelMap model, HttpServletRequest request) {

		transactionService.declineTransaction(request
				.getParameter("transactionID"));

		return "redirect:/regular/internalemployee-pendingtransaction";

	}

	//

	@RequestMapping(value = "/regular/modificiation-transction-approve", method = RequestMethod.POST)
	public String ModificationTransactionApprove(
			ModelMap model,
			@ModelAttribute("modificationTransaction") ModificationTransaction modificationTransaction,
			HttpServletRequest request, RedirectAttributes attr) {

		try {

			Transaction transaction = transactionService.getTransaction(request
					.getParameter("modifytransactionID"));

			if (modificationTransaction.getAmount() != null) {

				BigDecimal amount = new BigDecimal(
						modificationTransaction.getAmount());

				BigDecimal zeroamount = new BigDecimal(0);
				if ((amount.compareTo(zeroamount) == 0)
						|| (amount.compareTo(zeroamount) == -1)) {

					attr.addFlashAttribute("failureMsg",
							"Amount should be greater than zero");
					return "redirect:/regular/internalemployee-pendingtransaction";

				}

				transaction.setAmount(amount);
				transaction.setSenderAccNumber(modificationTransaction
						.getSenderAccNumber());
				transaction.setReceiverAccNumber(modificationTransaction
						.getRecieverAccNumber());

				Account accountSender = accountService
						.getAccountByNumber(modificationTransaction
								.getSenderAccNumber());

				if (accountSender == null) {

					attr.addFlashAttribute("failureMsg",
							"Invalid AccountNumber");
					return "redirect:/regular/internalemployee-pendingtransaction";
				}

				Account accountReciever = accountService
						.getAccountByNumber(modificationTransaction
								.getRecieverAccNumber());

				if (accountReciever == null) {

					attr.addFlashAttribute("failureMsg",
							"Invalid AccountNumber");
					return "redirect:/regular/internalemployee-pendingtransaction";

				}

				User user = userService.getUserbyCustomerID(accountSender
						.getUser().getCustomerID());
				if (user.getEmployeeOverride() == 1) {

					boolean result = transactionService
							.approveTransactionafterModification(transaction);

					if (!result) {
						attr.addFlashAttribute(
								"failureMsg",
								"Could not process your transaction. Debit amount cannot be higher than account balance. Please decline this transaction");
					} else {

						attr.addFlashAttribute("sucessMsg",
								"Transaction Approved Sucessfully with modifications");
					}
				} else {

					attr.addFlashAttribute("failureMsg",
							"You may not have the neccessary permissions to approve this transaction");
				}

			} else {

				attr.addFlashAttribute("failureMsg",
						"Please put to a valid amount to process this transaction");
				model.addAttribute("modificationTransaction",
						modificationTransaction);
				return "redirect:/regular/internalemployee-pendingtransaction";
			}

			return "redirect:/regular/internalemployee-pendingtransaction";

		} catch (Exception e) {

			attr.addFlashAttribute("failureMsg",
					"Please put to a valid amount to process this transaction");
			model.addAttribute("modificationTransaction",
					modificationTransaction);
			return "redirect:/regular/internalemployee-pendingtransaction";
		}
	}

	// /////////////////INTERNAL SETTINGS

	@RequestMapping(value = "/regular/int-employee-setting", method = RequestMethod.GET)
	public String getEmployeeSetting(ModelMap model) {

		System.out.println("inside get");
		User manager = userService.getUserDetails();
		model.addAttribute("manager", manager);
		model.addAttribute("email", manager.getEmail());
		return "employee/setting_employee";
	}

	@RequestMapping(value = "/regular/int-employee-setting_success", method = RequestMethod.POST)
	public String changeEmployeeSetting(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,RedirectAttributes attr) {

		User currentUser = userService.getUserDetails();

		// Validate fields
		user.setCustomerID(currentUser.getCustomerID());
		user.setEmail(currentUser.getEmail());
		user.setEmployeeOverride(currentUser.getEmployeeOverride());
		user.setEnabled(currentUser.getEnabled());
		user.setUserType(currentUser.getUserType());
		user.setCreatedAt(new DateTime().toLocalDateTime());
		user.setLastLoginAt(currentUser.getLastLoginAt());
		user.setPassword(currentUser.getPassword());
		user.setUpdatedAt(new DateTime().toLocalDateTime());

		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);

			attr.addFlashAttribute("failureMsg",
					"You have errors in your request:");

			return "redirect:/regular/int-employee-setting";
		}

		ModifiedUser modifiedUser = new ModifiedUser();
		modifiedUser.setCustomerID(currentUser.getCustomerID());
		modifiedUser.setAddressLine1(user.getAddressLine1());
		modifiedUser.setAddressLine2(user.getAddressLine2());
		modifiedUser.setPassword(currentUser.getPassword());
		modifiedUser.setEmail(currentUser.getEmail());
		modifiedUser.setEmployeeOverride(currentUser.getEmployeeOverride());
		modifiedUser.setEnabled(currentUser.getEnabled());
		modifiedUser.setFirstName(user.getFirstName());
		modifiedUser.setLastName(user.getLastName());
		modifiedUser.setMiddleName(user.getMiddleName());
		modifiedUser.setState(user.getState());
		modifiedUser.setZipCode(user.getZipCode());
		modifiedUser.setUserType(currentUser.getUserType());
		modifiedUser.setPhone(user.getPhone());
		modifiedUser.setCreatedAt(currentUser.getCreatedAt());
		modifiedUser.setUpdatedAt(new DateTime().toLocalDateTime());
		modifiedUser.setLastLoginAt(currentUser.getLastLoginAt());
		modifiedUser.setStatus("pending");
		modifiedUser.setEmployeeOverride(user.getEmployeeOverride());
		long epoch = System.currentTimeMillis() / 1000;
		modifiedUser.setRequestid(epoch);
		modifiedService.addRequest(modifiedUser);

		model.addAttribute("title", "Your Settings!");

		attr.addFlashAttribute("successMsg",
				"Your request was successful. Please wait for bank approval.");

		return "redirect:/regular/int-employee-home";
	}

	// //////////////******************END OF INTERNAL EMPLOYEE

	// /***************EXTERNAL REQUESTS

	@RequestMapping(value = "/manager/requests-pending-ext", method = RequestMethod.GET)
	public String getAdminSettingManager(ModelMap model) {

		List<ModifiedUser> musers = modifiedService
				.getAllExternalModifiedUsers();
		model.addAttribute("pendingModifications", musers);
		return "employee/pendingRequest_ext";
	}

	@RequestMapping(value = "/manager/requests-approve-ext", method = RequestMethod.GET)
	public String getApprovalPageManager(ModelMap model) {
		return "employee/approve_requests_ext";
	}

	@RequestMapping(value = "/manager/requests-view-ext", method = RequestMethod.POST)
	public String postApprovalePageManager(ModelMap model,
			HttpServletRequest request) {

		ModifiedUser modifiedUser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modificationrequestid"));

		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests_ext";
	}

	@RequestMapping(value = "/manager/approve-modification-ext", method = RequestMethod.POST)
	public String approveModificationManager(ModelMap model,
			HttpServletRequest request) {

		ModifiedUser modifieduser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modifiedrequestid"));

		modifiedService.approveRequest(modifieduser);
		return "redirect:/manager/requests-pending-ext";

	}

	@RequestMapping(value = "/manager/decline-modification-ext", method = RequestMethod.POST)
	public String declineModificationManager(ModelMap model,
			HttpServletRequest request) {

		ModifiedUser modifieduser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modifiedrequestid"));
		modifiedService.denyRequest(modifieduser);

		return "redirect:/manager/requests-pending-ext";
	}

	@RequestMapping(value = "/manager/back-modification-ext", method = RequestMethod.POST)
	public String backModificationManager(ModelMap model) {

		return "redirect:/manager/requests-pending-ext";
	}

	@RequestMapping(value = "/manager/internalemployee-pending-critical-transaction", method = RequestMethod.GET)
	public String getPendingCriticalTransactions(ModelMap model) {

		List<Transaction> pendingTransaction = transactionService
				.getPendingCriticalTransaction();
		model.addAttribute("pendingCriticalTransaction", pendingTransaction);
		return "employee/int_employee_pending_critical_transaction";
	}

	// ///**************INTERNAL REQUESTS

	@RequestMapping(value = "/admin/requests-pending", method = RequestMethod.GET)
	public String getAdminSettingSysAdmin(ModelMap model) {

		List<ModifiedUser> musers = modifiedService
				.getAllInternalModifiedUsers();
		model.addAttribute("pendingModifications", musers);
		return "employee/pendingRequest";
	}

	@RequestMapping(value = "/admin/requests-approve", method = RequestMethod.GET)
	public String getApprovalPage(ModelMap model) {
		return "employee/approve_requests";
	}

	@RequestMapping(value = "/admin/requests-view", method = RequestMethod.POST)
	public String postApprovalePage(ModelMap model, HttpServletRequest request) {

		ModifiedUser modifiedUser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modificationrequestid"));
		model.addAttribute("modifiedUser", modifiedUser);
		return "employee/approve_requests";
	}

	@RequestMapping(value = "/admin/approve-modification", method = RequestMethod.POST)
	public String approveModification(ModelMap model, HttpServletRequest request) {

		ModifiedUser modifieduser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modifiedrequestid"));

		modifiedService.approveRequest(modifieduser);
		return "redirect:/admin/requests-pending";

	}

	@RequestMapping(value = "/admin/decline-modification", method = RequestMethod.POST)
	public String declineModification(ModelMap model, HttpServletRequest request) {

		ModifiedUser modifieduser = modifiedService
				.findModifiedUserByRequestID(request
						.getParameter("modifiedrequestid"));
		modifiedService.denyRequest(modifieduser);

		return "redirect:/admin/requests-pending";
	}

	@RequestMapping(value = "/admin/back-modification", method = RequestMethod.POST)
	public String backModification(ModelMap model) {

		return "redirect:/admin/requests-pending";
	}

	// ***************************PII************************

	@RequestMapping(value = "/admin/sys-admin-PII", method = RequestMethod.GET)
	public String getPII(ModelMap model) {

		List<User> piiusers = internalUserService.getPIIUsersService();

		model.addAttribute("pii", piiusers);

		return "employee/PII_sys_admin";

	}

	@RequestMapping(value = "/admin/systemLog-sys-admin", method = RequestMethod.GET)
	public String getAllLogs(ModelMap model) {

		List<SystemLog> syslogs = systemLogService.getAllLog();

		model.addAttribute("systemlogs", syslogs);

		return "employee/systemLog_sys_admin";

	}
	
	//********************************Manager Change Password*********************************
	
	@RequestMapping(value = "/manager/password-manager-change", method = RequestMethod.GET)
	public String getconfirmPassword(ModelMap model) {		
		return "employee/manager_change_password";
	}
	@RequestMapping(value="/manager/managerpasswordsuccess", method = RequestMethod.POST)
    public String postChangePasswordMethod(ModelMap model,@ModelAttribute("changepassword") 
    ChangePassword changepassword,BindingResult result,RedirectAttributes attr) {
    	
		
		User customer = userService.getUserDetails();
		
		if(hashService.checkBCryptHash(changepassword.getCurrentpassword(), customer.getPassword())){
			
			if(changepassword.getConfirmpassword().equals(changepassword.getNewpassword())){
				
				internalUserService.updatePassword(customer.getEmail(), changepassword.getNewpassword());
				
				attr.addFlashAttribute("successMsg",
						"The password is changed successfully.");
				return "redirect:/manager/password-manager-change";
			}
			else{
				attr.addFlashAttribute("failureMsg",
						"The new password and confirm password are not same");
				return "redirect:/manager/password-manager-change";
			}
		}
		else
		{
			attr.addFlashAttribute("failureMsg",
					"The current password does not match");
			return "redirect:/manager/password-manager-change";
		 
		}
	}
	
	@RequestMapping(value = "/regular/password-regular-change", method = RequestMethod.GET)
	public String getregularconfirmPassword(ModelMap model) {		
		return "employee/regular_change_password";
	}
	@RequestMapping(value="/regular/regularpasswordsuccess", method = RequestMethod.POST)
    public String postregularChangePasswordMethod(ModelMap model,@ModelAttribute("changepassword") 
    ChangePassword changepassword,BindingResult result,RedirectAttributes attr) {
    	
		
		User customer = userService.getUserDetails();
		
		
		
		if(hashService.checkBCryptHash(changepassword.getCurrentpassword(), customer.getPassword())){
			
			if(changepassword.getConfirmpassword().equals(changepassword.getNewpassword())){
				
				internalUserService.updatePassword(customer.getEmail(), changepassword.getNewpassword());
				
				attr.addFlashAttribute("successMsg",
						"The password is changed successfully.Please login");
				return "redirect:/regular/password-regular-change";
			}
			else{
				attr.addFlashAttribute("failureMsg",
						"The new password and confirm password are not same");
				return "redirect:/regular/password-regular-change";
			}
		}
		else
		{
			attr.addFlashAttribute("failureMsg",
					"Current password does not match");
			return "redirect:/regular/password-regular-change";
		 
		}
	}
	
	/*@RequestMapping(value = "/admin/password-admin-change", method = RequestMethod.GET)
	public String getAdminconfirmPassword(ModelMap model) {
		User customer = userService.getUserDetails();
		
		return "employee/admin_change_password";
	}
	@RequestMapping(value="/admin/adminpasswordsuccess", method = RequestMethod.POST)
    public String postAdminChangePasswordMethod(ModelMap model,@ModelAttribute("changepassword") 
    ChangePassword changepassword,BindingResult result,RedirectAttributes attr) {
    	
		
		User customer = userService.getUserDetails();
		
		
		
		if(hashService.checkBCryptHash(changepassword.getCurrentpassword(), customer.getPassword())){
			
			if(changepassword.getConfirmpassword().equals(changepassword.getNewpassword())){
				
				internalUserService.updatePassword(customer.getEmail(), changepassword.getNewpassword());
				
				attr.addFlashAttribute("successMsg",
						"The password is changed successfully.Please login");
				return "redirect:/";
			}
			else{
				attr.addFlashAttribute("failureMsg",
						"The newpassword and confirm password are not same");
				return "redirect:/";
			}
		}
		else
		{
			attr.addFlashAttribute("failureMsg",
					"The currentpassword and Emailed password are not same");
			return "redirect:/";
		 
		}
	}
	*/
	

}