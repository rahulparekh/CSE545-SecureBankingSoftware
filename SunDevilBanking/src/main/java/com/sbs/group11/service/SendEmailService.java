/**
 * 
 */
package com.sbs.group11.service;
/**
 * @author SUNIL NEKKANTI
 *
 */
public interface SendEmailService {

	boolean sendEmail(String emailAddress, String subject, String content);

	boolean sendEmailWithAttachment(String emailAddress, String subject, String content);	
	
}
