package com.sbs.group11.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sbs.group11.dao.UserDao;

@SuppressWarnings("restriction")
@Service
public class PkiService {

	// @Autowired
	// private JavaMailSender mailSender;

	@Autowired
	private UserDao userDAO;

	@Transactional
	public String paymentinfoencryption(String userId, String oprivatekey) {

		try {

			com.sun.org.apache.xml.internal.security.Init.init();

			byte[] arr = Base64.decode(oprivatekey);

			PKCS8EncodedKeySpec info_private = new PKCS8EncodedKeySpec(arr);

			KeyFactory factory = KeyFactory.getInstance("RSA");

			PrivateKey pkey = factory.generatePrivate(info_private);
			System.out.println(pkey.toString());

			Cipher generateCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			generateCipher.init(Cipher.ENCRYPT_MODE, pkey);
			//System.out.println(" 1 ");

			byte[] testvals = generateCipher.doFinal(Base64.decode(userId));
			BASE64Encoder b64 = new BASE64Encoder();
		
			String encryptedString = b64.encode(testvals);
			

			return encryptedString;
			
		} catch (Exception e) {
			return null;
			//e.printStackTrace();
		}
	
	}

	@SuppressWarnings("finally")
	@Transactional
	public String generatekeypair(String mailer) {

		KeyPairGenerator keysgenrated = null;
		String publickey = null;
		try {

			keysgenrated = KeyPairGenerator.getInstance("RSA");
			keysgenrated.initialize(2048);

			KeyPair pairs = keysgenrated.generateKeyPair();
			KeyFactory factory = KeyFactory.getInstance("RSA");
			BASE64Encoder encoder = new BASE64Encoder();

			X509EncodedKeySpec info_public = factory.getKeySpec(
					pairs.getPublic(), X509EncodedKeySpec.class);

			// create publicKey
			publickey = encoder.encode(info_public.getEncoded());
			System.out.println("Public Key : " + publickey);

			PKCS8EncodedKeySpec info_private = factory.getKeySpec(
					pairs.getPrivate(), PKCS8EncodedKeySpec.class);

			// create private key
			String privatekey = encoder.encode(info_private.getEncoded());
			System.out.println(("Private Key " + "\n" + privatekey));
			SendEmailService email = new SendEmailServiceImpl();
			email.sendEmail(mailer, "Your private Key", "Private Key " + "\n"
					+ privatekey + "\n");
			
		//	System.out.println(" This is in key pair generation " + publickey);
			
			

		} catch (Exception e) {
			System.out.println(e);
		}finally{
			return publickey;
		}
		//return null;  //unreachable code
	}

	@Transactional
	public boolean paymentinfodecryption(String userId, String payment) {


		if(payment==null){
			return false;
		}
		String alias_publicKey = userDAO.findByCustomerID(userId).getPublicKey();
		System.out.println(alias_publicKey);

		byte[] decryption = null;
	
		try {
		
			decryption = Base64.decode(payment);
			
		} catch (Base64DecodingException e1) {

			System.out.println(" Incatch ");
			e1.printStackTrace();
		}

		try {
			

			byte[] arr = Base64.decode(alias_publicKey);
	
			X509EncodedKeySpec info_public = new X509EncodedKeySpec(arr);
		
			KeyFactory factory = KeyFactory.getInstance("RSA");
			
			PublicKey publicKey = factory.generatePublic(info_public);
			

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			
		

			byte[] userIddecrypt = cipher.doFinal(decryption);
		

			String decryptedUserId = Base64.encode(userIddecrypt);
	
			System.out.println("decryptedUserId  is **** "+decryptedUserId);
			
			System.out.println("userId  is **** "+userId);
			
			if (decryptedUserId.equals(userId)) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}