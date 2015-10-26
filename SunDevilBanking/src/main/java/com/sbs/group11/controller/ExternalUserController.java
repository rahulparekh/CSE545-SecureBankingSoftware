package com.sbs.group11.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;
import com.sbs.group11.service.AccountService;
import com.sbs.group11.service.TransactionService;
import com.sbs.group11.service.UserService;

/**
 * UserController: Controls most of our application UI paths for all types of
 * users including internal users.
 * 
 * Matches all urls with the path /home/**
 * 
 * @author Rahul
 */
@Controller
@RequestMapping(value = "/home")
public class ExternalUserController {

	final static Logger logger = Logger.getLogger(ExternalUserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	SmartValidator validator;

	/**
	 * Gets the home.
	 *
	 * @param model
	 *            the model
	 * @return the home
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		User user = userService.getUserDetails();
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/home";
	}

	/**
	 * Gets the credit and debit page.
	 *
	 * @param model
	 *            the model
	 * @return the credit debit
	 */
	@RequestMapping(value = "/credit-debit", method = RequestMethod.GET)
	public String getCreditDebit(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/creditdebit";
	}

	/**
	 * Post credit debit.
	 *
	 * @param model
	 *            The Spring ModelMap
	 * @param request
	 *            HttpServlet request
	 * @param transaction
	 *            ModelAttribute transaction
	 * @param result
	 *            the BindingResult used for validation
	 * @param attr
	 *            RedirectAttribute used to pass error/success messages
	 * @return the string
	 */
	@RequestMapping(value = "/credit-debit", method = RequestMethod.POST)
	public String postCreditDebit(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction transaction,
			BindingResult result, RedirectAttributes attr) {

		// Get user details
		User user = userService.getUserDetails();
		model.put("user", user);

		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/credit-debit";
		}

		BigDecimal amount = transactionService.getBigDecimal(request
				.getParameter("amount"));

		// create the transaction object
		transaction = new Transaction(
				transactionService.getUniqueTransactionID(), 
				"Self " + request.getParameter("type"),
				request.getParameter("number"), 
				request.getParameter("number"),
				"pending", 
				request.getParameter("type"),
				amount,
				request.getParameter("number")
				);

		// Validate the model
		validator.validate(transaction, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.transaction",
					result);
			attr.addFlashAttribute("transaction", transaction);

			// redirect to the credit debit view page
			return "redirect:/home/credit-debit";
		}

		// Check if Debit amount is < balance in the account
		if (request.getParameter("type").equalsIgnoreCase("debit")
				&& amount.compareTo(account.getBalance()) >= 0) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance");
			return "redirect:/home/credit-debit";
		}

		transactionService.addTransaction(transaction);

		attr.addFlashAttribute(
				"successMsg",
				"Transaction completed successfully. Transaction should show up on your account shortly.");

		// redirect to the credit debit view page
		return "redirect:/home/credit-debit";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.GET)
	public String getStatements(ModelMap model, HttpServletRequest request) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Account Statements");
		model.addAttribute("accounts", accounts);

		return "customer/statements";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.POST)
	public String postStatements(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Account Statements " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);
		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried statements functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/statements";
		}

		List<StatementMonthYear> statements = transactionService
				.getStatementMonths(request.getParameter("number"));
		if (statements.size() > 0) {
			logger.debug("Received statements: " + statements.get(0).getMonth());
		}
		model.addAttribute("statements", statements);
		model.addAttribute("accNumber", request.getParameter("number"));

		return "customer/statements";
	}

	@RequestMapping(value = "/statements/view", method = RequestMethod.POST)
	public String postViewStatement(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr) {

		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		if (account == null) {
			logger.warn("Someone tried view statement functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/statements";
		}

		List<Transaction> transactions = transactionService
				.getCompletedTransactionsByAccountNummber(
						request.getParameter("number"),
						request.getParameter("month"),
						Integer.parseInt(request.getParameter("year")));

		model.addAttribute("title", "Account Statements");
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("transactions", transactions);
		model.addAttribute("statementName", request.getParameter("month") + " "
				+ request.getParameter("year"));

		return "customer/statement";
	}

	@RequestMapping(value = "/statements/download", method = RequestMethod.POST)
	public ModelAndView postDownloadStatement(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		if (account == null) {
			logger.warn("Someone tried view statement functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return new ModelAndView("redirect:/home/statements");
		}

		List<Transaction> transactions = transactionService
				.getCompletedTransactionsByAccountNummber(
						request.getParameter("number"),
						request.getParameter("month"),
						Integer.parseInt(request.getParameter("year")));

		model.addAttribute("title", "Account Statements");
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("transactions", transactions);
		model.addAttribute("statementName", request.getParameter("month") + " "
				+ request.getParameter("year"));

		return new ModelAndView("pdfView", "model", model);
	}

	@RequestMapping(value = "/fund-transfer", method = RequestMethod.GET)
	public String getFundTransfer(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/fundtransfer";
	}

	@RequestMapping(value = "/fund-transfer", method = RequestMethod.POST)
	public String postFundTransfer(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction senderTransaction,
			BindingResult result, RedirectAttributes attr) {

		// Get user details
		User user = userService.getUserDetails();
		model.put("user", user);
		
		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("senderAccNumber"), accounts);

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/fund-transfer";
		}

		boolean isTransferAccountValid = transactionService.isTransferAccountValid(
				accountService, transactionService, accounts,
				request, model, user, attr);
		
		logger.debug("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {
			
			BigDecimal amount = transactionService.getBigDecimal(request
					.getParameter("amount"));
			
			String receiverAccNumber = "";
			if (request.getParameter("type").equalsIgnoreCase("internal")) {
				receiverAccNumber = request.getParameter("receiverAccNumber");
				logger.info("internal transfer");
			} else {
				receiverAccNumber = request.getParameter("receiverAccNumberExternal");
				logger.info("external transfer");
			}
			
			logger.debug("receiverAccNumber: " + receiverAccNumber);
	
			// create the transaction object
			senderTransaction = new Transaction(
					transactionService.getUniqueTransactionID(), 
					"Fund Transfer",
					receiverAccNumber,
					request.getParameter("senderAccNumber"), 
					"completed", 
					"Debit",
					amount, 
					request.getParameter("senderAccNumber")
				);
			
			logger.debug("Sender Transaction created: " + senderTransaction);
	
			// Validate the model
			validator.validate(senderTransaction, result);
			logger.debug("Validated model");
			
			if (result.hasErrors()) {
				logger.debug("Validation errors: ");
				logger.debug(result);
	
				// attributes for validation failures
				attr.addFlashAttribute(
						"org.springframework.validation.BindingResult.transaction",
						result);
				attr.addFlashAttribute("transaction", senderTransaction);
	
				// redirect to the credit debit view page
				return "redirect:/home/fund-transfer";
			}
			
			logger.debug("No validation errors");
	
			// Check if Debit amount is < balance in the account
			if ( amount.compareTo(account.getBalance()) >= 0) {
				logger.debug("Debit < Balance");
				attr.addFlashAttribute(
						"failureMsg",
						"Could not process your transaction. Debit amount cannot be higher than account balance");
				return "redirect:/home/fund-transfer";
			}
			
			Transaction receiverTransaction = new Transaction(
					transactionService.getUniqueTransactionID(), 
					"Fund Transfer",
					receiverAccNumber,
					request.getParameter("senderAccNumber"), 
					"completed", 
					"Credit",
					amount,
					receiverAccNumber
				);
			
			logger.debug("Receiver Transaction created: " + receiverTransaction);
			
			try {
				logger.debug("Trying to transfer funds");
				accountService.transferFunds(transactionService, accountService, senderTransaction, receiverTransaction, amount);
			} catch (Exception e) {
				logger.warn(e);
				attr.addFlashAttribute(
						"failureMsg",
						"Transfer unsucessful. Please try again or contact the bank.");
				return "redirect:/home/fund-transfer";
			}
	
			attr.addFlashAttribute(
					"successMsg",
					"Transaction completed successfully. Transaction should show up on your account shortly.");
			
		} else {
			attr.addFlashAttribute(
					"failureMsg",
					"Transfer unsucessful. Please try again or contact the bank.");
		}

		// redirect to the view page
		return "redirect:/home/fund-transfer";
	}
	
	/**
	 * Gets the payment requests for customers.
	 *
	 * @param model the model
	 * @return view
	 */
	@RequestMapping(value = "/payment-requests", method = RequestMethod.GET)
	public String getPaymentRequestsForCustomer(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		return "customer/customerpayments";
		 
	}
	
	
	/**
	 * Gets the payments for merchants.
	 *
	 * @param model the model
	 * @return the payments for merchants
	 */
	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String getPaymentsForMerchants(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		return "customer/merchantpayments";		 
	}

}
