package com.sbs.group11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sbs.group11.model.User;
import com.sbs.group11.service.InternalUserService;

@Controller
public class InternalUserController {
	
	@Autowired
	InternalUserService internalUserService;

	@ModelAttribute("user")
	public User getUserObject() {
		return new User();
	}

	@RequestMapping(value = "/manage-employee", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("fullname", "Arun the Boss");
		return "employee/manage_employees";
	}

	@RequestMapping(value = "/manage-employee", method = RequestMethod.POST)
	public String AddInternalUser(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result) {
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("user", "Sriram the Boss");
		internalUserService.addInternalUser(user);

		return "employee/manage_employees";
	}
}