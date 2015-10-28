package com.sbs.group11.service;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.spec.InvalidKeySpecException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;


import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;


import sun.misc.BASE64Encoder;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import com.sbs.group11.dao.UserDao;
import com.sbs.group11.model.User;

@Service
public class PkiService{
	

@Autowired
private JavaMailSender mailSender;

@Autowired
private UserDao userDAO;

	
		
	
	

	@Transactional
	public String paymentinfoencryption(String userId, String oprivatekey) 
	{
	
		
		//get corresponding userName of userID
		String userName = userDAO.findByCustomerID(userId).getFirstName();
		
		try
		{
			
		com.sun.org.apache.xml.internal.security.Init.init(); // Initialize xml internal security init method
		
		byte[] arr = Base64.decode(oprivatekey);
	
		
		PKCS8EncodedKeySpec info_private = new PKCS8EncodedKeySpec(arr);
	
		KeyFactory factory = KeyFactory.getInstance("RSA");
		
		PrivateKey pkey = factory.generatePrivate(info_private);
		
		Cipher generateCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		generateCipher.init(Cipher.ENCRYPT_MODE, pkey); 
		
		byte[] testvals =  generateCipher.doFinal(Base64.decode(userName));
		
		BASE64Encoder b64 = new BASE64Encoder();
		String encryptedString= b64.encode(testvals);
		
		
		return encryptedString;
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return null;
	}
	
	
	

	@Transactional
	public String generatekeypair(String mailer){
		
		KeyPairGenerator keysgenrated = null;
		
		try{
			
			keysgenrated = KeyPairGenerator.getInstance("RSA");
			keysgenrated.initialize(2048);
			
			KeyPair pairs = keysgenrated.generateKeyPair();
			KeyFactory factory = KeyFactory.getInstance("RSA");
			BASE64Encoder encoder = new BASE64Encoder();

			X509EncodedKeySpec  info_public = factory.getKeySpec(pairs.getPublic(),
					X509EncodedKeySpec.class);
			
			//create publicKey
			String publickey = encoder.encode(info_public.getEncoded());

			PKCS8EncodedKeySpec  info_private = factory.getKeySpec(
					pairs.getPrivate(), PKCS8EncodedKeySpec .class);
			
			
			//create private key
			String privatekey = encoder.encode(info_private.getEncoded());
			System.out.println(("Private Key "+"\n"+privatekey));
			
			
					
			
			//Send an email to the user
			SimpleMailMessage messageemail = new SimpleMailMessage();
	        messageemail.setTo(mailer);
	        messageemail.setSubject("Your private Key");
	        messageemail.setText("Private Key "+"\n"+privatekey+"\n");
	        
	        mailSender.send(messageemail); 
			
			//Return the public key to the new User registration object
			return publickey;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null; // Unreachable code !!
	}
	

	@Transactional
	public boolean paymentinfodecryption(String userId, String payment)
	{
		
		String alias_publicKey = userDAO.findByCustomerID(userId).getPublicKey();
		
		byte[] decryption = null;
			try {
				decryption= Base64.decode(payment);
			} catch (Base64DecodingException e1) {
				
				e1.printStackTrace();
			}
		
		String userName = userDAO.findByCustomerID(userId).getFirstName(); 
		
		try
		{
	
		byte[] arr = Base64.decode(alias_publicKey);
		X509EncodedKeySpec info_public = new X509EncodedKeySpec(arr);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = factory.generatePublic(info_public);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, publicKey); 
		
		byte[] usernamedecrypt = cipher.doFinal(decryption);
		
		String uname= Base64.encode(usernamedecrypt);
		
		
		if(uname.equals(userName)) 
		{
			return true; 
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return false;
	}
}
