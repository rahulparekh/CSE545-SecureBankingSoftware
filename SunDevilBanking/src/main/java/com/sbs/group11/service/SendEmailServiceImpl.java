/**
 * 
 */
package com.sbs.group11.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@ComponentScan({ "com.sbs.group11.configuration" })
@PropertySource(value = { "classpath:application.properties" })
/**
 * @author SUNIL NEKKANTI
 *
 * Send the E-mail to any User's email address invoking the sendEmail  method.
 * 
 * Example : sendEmail("something@xyz.com", "any subject" , "any content")
 * 
 * Refer  application.properties for environmental variables.
 */


public class SendEmailServiceImpl implements SendEmailService {
	
	@Autowired
	private Environment environment;
	
	public boolean sendEmail(String emailAddress, String subject, String content)
	{
		  boolean emailStatus = false;  

		  Properties props = new Properties();  
		  props.put("mail.smtp.host", environment.getRequiredProperty("mail.smtp.host"));  
		  props.put("mail.smtp.socketFactory.port", environment.getRequiredProperty("mail.smtp.socketFactory.port"));  
		  props.put("mail.smtp.socketFactory.class",  
				  environment.getRequiredProperty("mail.smtp.socketFactory.class"));  
		  props.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));  
		  props.put("mail.smtp.port", environment.getRequiredProperty("mail.smtp.port"));  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(environment.getRequiredProperty("EmailAddress"),environment.getRequiredProperty("EmailPassword"));  
		   }  
		  });  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress(environment.getRequiredProperty("EmailAddress"))); 
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailAddress));  
		   message.setSubject(subject);  
		   message.setText(content);  
		     
		   //send message  
		   Transport.send(message);  
		   
		   emailStatus = true;
		   
		  } catch (MessagingException e)
		  { 
			  throw new RuntimeException(e);
		  }  
		
		  return emailStatus;  
	}
	

}
