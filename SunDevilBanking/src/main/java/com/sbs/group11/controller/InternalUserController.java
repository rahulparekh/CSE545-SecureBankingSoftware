package com.sbs.group11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sbs.group11.model.*;
import com.sbs.group11.service.InternalUserService;

@Controller
public class InternalUserController {
	
	@Autowired
	InternalUserService internalUserService;

	@ModelAttribute("user")
	public User getUserObject() {
		return new User();
	}
	
	@ModelAttribute("empSearch")
	public EmployeeSearch getempSearchObject() {
		return new EmployeeSearch();
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
		
		model.addAttribute("title", "Welcome System Admin:");
		User user = internalUserService.searchInternalUser(empSearch.getEmployeeID());
		System.out.println(user.getFirstName());
		
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("user", user);
		
		return "redirect:/manage-employee";
	}
}