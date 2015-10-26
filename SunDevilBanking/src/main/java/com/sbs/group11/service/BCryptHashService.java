package com.sbs.group11.service;

/**
 * @author Rahul
 * 
 *         BCryptHashService.
 * 
 *         Usage: To generate hashed strings using the BCrypt algorithm and to
 *         verify whether a plaintext string matches a hashed string
 * 
 */
public interface BCryptHashService {

	/**
	 * Gets the hashed strings using the BCrypt algorithm
	 *
	 * @param password
	 *            the password
	 * @return the b crypt hash
	 */
	String getBCryptHash(String plaintext);

	/**
	 * Verifies whether a plaintext password matches a BCrypt hash value
	 *
	 * @param password
	 *            the password
	 * @param storedPassword
	 *            the stored password
	 * @return true, if successful
	 */
	boolean checkBCryptHash(String plaintext, String hash);
}
