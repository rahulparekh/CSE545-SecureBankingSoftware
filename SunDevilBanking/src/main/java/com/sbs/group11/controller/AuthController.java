package com.sbs.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
    public String getLogin(ModelMap model) {
		model.addAttribute("title", "Welcome! Please Login");
        return "auth/login";
    }
 
    @RequestMapping(value="/forgotpass", method = RequestMethod.GET)
    public String getForgotPassword(ModelMap model) {
    	model.addAttribute("title", "Forgot Password");
        return "auth/forgotpass";
    }
    
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public String getHome(ModelMap model) {
		model.addAttribute("title", "Welcome John");
		model.addAttribute("user", "John Doe");
        return "customer/home";
    }


}
