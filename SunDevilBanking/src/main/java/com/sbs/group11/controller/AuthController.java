package com.sbs.group11.controller;

import java.util.Random;
import java.util.Set;

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
import com.sbs.group11.model.SecurityQuestion;
import com.sbs.group11.model.User;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.SendEmailService;
import com.sbs.group11.service.UserService;
//import com.sbs.group11.service.SecurityQuesService;


@Controller
public class AuthController {
	
	final static Logger logger = Logger.getLogger(AuthController.class);
	
	@Autowired
	InternalUserService internalUserService;	

	@Autowired
	SendEmailService sendEmailService;	

	
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
    	User user = internalUserService.findUserByEmail(emailSearch.getEmail());
    	if(user == null)
    	{
    			attr.addFlashAttribute("failureMsg",
    					"Not a Valid User");
    			return "redirect:/forgotpass";
    		
    	}
    	
    	Set <SecurityQuestion> secques = user.getSecurityQuestions();
    	
    	int i = 1;
    	
    	for (SecurityQuestion ques: secques ){
    		model.addAttribute("question"+Integer.toString(i),ques.getQuestion());		
    		i++;
    	}
    	
		
        return "auth/securityquestions";
         
    }
    
    public Long generateRandomNumberOfLength(int length) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (ran.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (ran.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}
    
    
            
    @RequestMapping(value="/confirmpassword", method = RequestMethod.POST)
    public String postValidateSecurityQuestions(ModelMap model,@ModelAttribute("SecurityQues") 
    SecurityQues securityQues,BindingResult result,RedirectAttributes attr) {
    	
    	System.out.println(securityQues.getEmail());
    	User user = internalUserService.findUserByEmail(securityQues.getEmail());
    	if(user == null)
    	{
    			attr.addFlashAttribute("failureMsg",
    					"Not a Valid User");
    			return "redirect:/forgotpass";
    		
    	}
    	Set <SecurityQuestion> secques = user.getSecurityQuestions();
    	
    	
    	for (SecurityQuestion ques: secques ){
    		if(ques.getQuestion().equals(securityQues.getQuestion1()))
    		{
    			if((!ques.getAnswer().equals(securityQues.getAnswer1())))
    			{
    				
    				attr.addFlashAttribute("failureMsg",
    						"The answers entered are not as same as saved ones");
    				return "redirect:/forgotpass";
    			}
    		}
    		else if(ques.getQuestion().equals(securityQues.getQuestion2()))
    		{
    			if(!ques.getAnswer().equals(securityQues.getAnswer2()))
    			{
    				
    				attr.addFlashAttribute("failureMsg",
    						"The answers entered are not as same as saved ones");
    				return "redirect:/forgotpass";
    			}
    			
    		}
    		else if(ques.getQuestion().equals(securityQues.getQuestion3()))
    		{
    			if(!ques.getAnswer().equals(securityQues.getAnswer3()))
    			{
    				
    				attr.addFlashAttribute("failureMsg",
    						"The answers entered are not as same as saved ones");
    				return "redirect:/forgotpass";
    			}
    		}
    		
    	}
    	
    	String tempPassword = "" + generateRandomNumberOfLength(9);
		
    	internalUserService.updatePassword(user.getEmail(), tempPassword);
		sendEmailService.sendEmail(user.getEmail(), "Temporary Password Reset", "Your Temporary password is"+tempPassword);

		attr.addFlashAttribute("successMsg",
				"A temporary  password is generated and sent to you.Please use it to login");
		return "redirect:/";
       
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
