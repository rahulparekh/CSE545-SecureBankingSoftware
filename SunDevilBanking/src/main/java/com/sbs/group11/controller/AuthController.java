package com.sbs.group11.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.ChangePassword;
import com.sbs.group11.model.EmailSearch;
import com.sbs.group11.model.SecurityQues;
import com.sbs.group11.model.User;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.SecurityQuesService;


@Controller
public class AuthController {
	
	final static Logger logger = Logger.getLogger(AuthController.class);
	@Autowired
	SecurityQuesService securityQuesService;
	@Autowired
	InternalUserService internalUserService;
	
	

	@ModelAttribute("user")
	public User getUserObject() {
		return new User();
	}
	@ModelAttribute("securityques")
	public SecurityQues getsecurityQuesObject() {
		return new SecurityQues();
		
	}
	
	@ModelAttribute("changepassword")
	public ChangePassword getChangePasswordObject() {
		return new ChangePassword();
		
	}
	
	@ModelAttribute("emailSearch")
	public EmailSearch getEmailSearchObject() {
		return new EmailSearch();
		
	}
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String getLogin(ModelMap model) {
		model.addAttribute("title", "Welcome! Please Login");
        return "auth/login";
    }
 
    @RequestMapping(value="/forgotpass", method = RequestMethod.GET)
    public String getForgotPassword(ModelMap model) {
    	model.addAttribute("title", "Forgot Password");
        return "auth/forgotpass";
    }
    
    @RequestMapping(value="/securityquestions", method = RequestMethod.POST)
    public String postSecurityQuestions(ModelMap model,@ModelAttribute("emailSearch") 
    EmailSearch emailSearch,BindingResult result,RedirectAttributes attr){
    	model.addAttribute("title", "Forgot Password");
    	System.out.println(emailSearch.getEmail());
    	SecurityQues securityques = securityQuesService.findSecurityquesByEmail(emailSearch.getEmail());
    	if(securityques == null)
    	{
    			attr.addFlashAttribute("failureMsg",
    					"Not a Valid User");
    			return "redirect:/forgotpass";
    		
    	}
		
		model.addAttribute("question1",securityques.getQuestion1());
		model.addAttribute("question2",securityques.getQuestion2());
		model.addAttribute("question3",securityques.getQuestion3());
        return "auth/securityquestions";
         
    }
    
    
            
    @RequestMapping(value="/confirmpassword", method = RequestMethod.POST)
    public String postValidateSecurityQuestions(ModelMap model,@ModelAttribute("SecurityQues") 
    SecurityQues securityQues,BindingResult result,RedirectAttributes attr) {
    	
    	System.out.println(securityQues.getEmail());
    	SecurityQues securityques1 = securityQuesService.findSecurityquesByEmail(securityQues.getEmail());
    	if(securityques1 == null)
    	{
    			attr.addFlashAttribute("failureMsg",
    					"Not a Valid User address");
    			return "redirect:/forgotpass";
    		
    	}
		if((securityques1.getAnswer1().equals(securityQues.getAnswer1())) &&  (securityques1.getAnswer2().equals(securityQues.getAnswer2()) 
				&& (securityques1.getAnswer3().equals(securityQues.getAnswer3()))))
			return "auth/confirmpassword";
		
		else
			attr.addFlashAttribute("failureMsg",
					"The answers entered are not as same as saved ones");
			return "redirect:/forgotpass";	
       
    }
    
    @RequestMapping(value="/passwordSuccessful", method = RequestMethod.POST)
    public String postChangePasswordMethod(ModelMap model,@ModelAttribute("changepassword") 
    ChangePassword changepassword,BindingResult result,RedirectAttributes attr) {
    	model.addAttribute("title", "Change Password");
    	
    	User user = internalUserService.findUserByEmail(changepassword.getEmail());
    	if(user == null){
    		attr.addFlashAttribute("failureMsg",
					"Not a Valid User address");
			return "redirect:/forgotpass";
    	}
    	
    	if(user.getPassword().equals(changepassword.getNewpassword())){
    		
    		attr.addFlashAttribute("failureMsg",
					"The new password is same as old one.Please enter a new thing");
			return "redirect:/forgotpass";	
    	}
    	
    	if(changepassword.getNewpassword().equals(changepassword.getConfirmpassword())){
    		
    		internalUserService.updatePassword(changepassword.getEmail(), changepassword.getNewpassword());
    	
    		attr.addFlashAttribute("successMsg",
					"The password is changed successfully.Please login");
			return "redirect:/";	
        
    	}
        else
        	
        	attr.addFlashAttribute("failureMsg",
					"The newpassword and confirm password are not same");
			return "redirect:/forgotpass";	
        
    }
    
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String getLogout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/?logout";
    }


}
