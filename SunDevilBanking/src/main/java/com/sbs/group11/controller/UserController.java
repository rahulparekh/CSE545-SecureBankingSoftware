package com.sbs.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class UserController {

	@RequestMapping(value="/", method = RequestMethod.GET)
    public String getHome(ModelMap model) {
		model.addAttribute("title", "Welcome John");
		model.addAttribute("user", "John Doe");
        return "customer/home";
    }
}
