package com.sbs.group11.controller;

import java.util.LinkedHashMap;
import java.util.Map;

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
		Map<String,String> userTypes = new LinkedHashMap<String,String>();
		System.out.println("user name is"+user.getCustomerID());
		userTypes.put("regular", "regular");
		userTypes.put("manager", "manager");
		model.addAttribute("user", user);
		model.addAttribute("userTypes", userTypes);
		return "employee/manage_employees";
	}

	@RequestMapping(value = "/manage-employee_success", method = RequestMethod.POST)
	public String AddInternalUser(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result) {
		System.out.println("inside controller");
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("user", "Sriram the Boss");
		System.out.println(user.getFirstName());
		model.addAttribute("user",new User());
		internalUserService.addInternalUser(user);
		
		return "redirect:/sysadmin-home";
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
		System.out.println(user.getFirstName());
		redirectAttrs.addFlashAttribute("user", user);
		return "redirect:/manage-employee";
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
		user.setuserType("admin");
		internalUserService.updateInternalUser(user);
		return "redirect:/sysadmin-home";
	}
	
	
}