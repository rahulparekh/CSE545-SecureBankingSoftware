package com.sbs.group11.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("BCryptHashService")
public class BCryptHashServiceImpl implements BCryptHashService {

	public String getBCryptHash(String plaintext) {
		
		if (plaintext != null && !plaintext.isEmpty()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
	        return encoder.encode(plaintext);
		}		
        
		return null;
	}
	
	public boolean checkBCryptHash(String plaintext, String hash) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		return encoder.matches(plaintext, hash);
		
	}
	

}
