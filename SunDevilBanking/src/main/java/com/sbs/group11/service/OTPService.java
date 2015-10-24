/**
 * 
 */
package com.sbs.group11.service;

/**
 * @author SUNIL NEKKANTI
 *
 */
public interface OTPService {
	
	String generateOTP(byte[] secretcode,
			           long dynamicNumber,
			           int otpLength,
					   boolean checksumFlag,
					   int terminalOffset);
	
	int computeChecksum(long checksumNumber, int significantDigits);
	
	byte[] hmac_sha512(byte[] hmacbytes, byte[] authenticateText);
	

}
