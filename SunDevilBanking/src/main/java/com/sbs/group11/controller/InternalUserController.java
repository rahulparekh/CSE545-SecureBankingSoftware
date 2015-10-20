package com.sbs.group11.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sbs.group11.model.*;
import com.sbs.group11.service.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")
public class InternalUserController {

	InternalUserService user_service;
	@RequestMapping(value="/ManageEmployee", method = RequestMethod.GET)
    public String getHome(ModelMap model) {
		System.out.println("inside controlller");
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("user", "Arun the Boss");
        return "employee/manage_employees";
    }
	
	@RequestMapping(value="/ManageEmployee", method = RequestMethod.POST)
    public String AddInternalUser(ModelMap model, @ModelAttribute("InternalUser") 
    InternalUser user, BindingResult result) {
		model.addAttribute("title", "Welcome System Admin:");
		model.addAttribute("user", "Sriram the Boss");
		user_service.addInternalUser(user);
		
        return "employee/manage_employees";
    }
}