/**
 * 
 */
package com.sbs.group11.service;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author SUNIL NEKKANTI
 *
 *         Send the E-mail to any User's email address invoking the sendEmail
 *         method.
 * 
 *         Example : sendEmail("something@xyz.com", "any subject" ,
 *         "any content")
 * 
 *         Refer application.properties for environmental variables.
 */

@ComponentScan({ "com.sbs.group11.configuration" })
@PropertySource(value = { "classpath:application.properties" })
@Service("SendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

	@Autowired
	private Environment environment;

	public boolean sendEmail(String emailAddress, String subject, String content) {
		boolean emailStatus = false;

		Properties props = new Properties();
		props.put("mail.smtp.host",
				"smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port",
				"465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth",
				"true");
		props.put("mail.smtp.port",
				"465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("sundevilsbank11@gmail.com",
								"devilbank");
					}
				});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sundevilsbank11@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					emailAddress));
			message.setSubject(subject);
			message.setText(content);

			// send message
			Transport.send(message);

			emailStatus = true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		return emailStatus;
	}
	
	public boolean sendEmailWithAttachment(String emailAddress, String subject, String content){
		boolean emailStatus = false;

		Properties props = new Properties();
		props.put("mail.smtp.host",
				"smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port",
				"465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth",
				"true");
		props.put("mail.smtp.port",
				"465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("sundevilsbank11@gmail.com",
								"devilbank");
					}
				});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sundevilsbank11@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					emailAddress));
			message.setSubject(subject);
			message.setText(content);
			
			
			FileUtils.writeStringToFile(new File("confidential.txt"), content);
			

			//Attachment
			 BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart = new MimeBodyPart();
	         
	         	DataSource source = new FileDataSource("confidential.txt");
	         	messageBodyPart.setDataHandler(new DataHandler(source));
	         	messageBodyPart.setFileName("confidential.txt");
	         
		 	Multipart multipart = new MimeMultipart();

	         	// Set text message part
	        	multipart.addBodyPart(messageBodyPart);
	    

	         	message.setContent(multipart);
			

			// send message
			Transport.send(message);

			emailStatus = true;

			} catch (MessagingException e) {
			throw new RuntimeException(e);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emailStatus;
		
		
	}

}
