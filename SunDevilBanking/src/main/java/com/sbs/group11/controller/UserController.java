package com.sbs.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class UserController {

	/**
	 * Gets the home.
	 *
	 * @param model
	 *            the model
	 * @return the home
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		model.addAttribute("title", "Welcome John");
		model.addAttribute("user", "John Doe");
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
		model.addAttribute("title", "Credit and Debit Funds");
		model.addAttribute("user", "John Doe");
		return "customer/creditdebit";
	}
}
