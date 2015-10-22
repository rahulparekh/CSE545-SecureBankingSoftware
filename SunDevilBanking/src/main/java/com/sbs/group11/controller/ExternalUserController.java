package com.sbs.group11.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.Account;
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
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/creditdebit";
	}

	@RequestMapping(value = "/credit-debit", method = RequestMethod.POST)
	public String postCreditDebit(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction transaction,
			BindingResult result, RedirectAttributes attr) {

		// Get user details
		User user = userService.getUserDetails();

		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		// If account is empty or null, skip the account service check
		if (request.getParameter("number") != null
				&& !request.getParameter("number").isEmpty()) {

			// See if the account submitted in the request
			// is belongs to the user. This will protect us against
			// updating any user account through any other user
			boolean isAccount = false;
			for (Account account : accounts) {
				if (account.getNumber().equals(request.getParameter("number"))) {
					isAccount = true;
					break;
				}
			}

			// Exit the transaction if Account doesn't exist
			if (!isAccount) {
				logger.warn("Someone tried credit/debit functionality for some other account. Details:");
				logger.warn("Credit/Debit Acc No: "
						+ request.getParameter("number"));
				logger.warn("Customer ID: " + user.getCustomerID());
				model.addAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return "customer/creditdebit";
			}

		}

		// create the transaction object
		transaction = new Transaction(
				transactionService.getUniqueTransactionID(),
				request.getParameter("number"),
				request.getParameter("number"),
				"pending",
				request.getParameter("type"),
				transactionService.getBigDecimal(request.getParameter("amount")));
		
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

		transactionService.addTransaction(transaction);

		attr.addFlashAttribute(
				"successMsg",
				"Transaction completed successfully. Transaction should show up on your account shortly.");
		
		// redirect to the credit debit view page
		return "redirect:/home/credit-debit";
	}
}
