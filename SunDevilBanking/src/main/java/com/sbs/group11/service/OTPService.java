/**
 * 
 */
package com.sbs.group11.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.sbs.group11.model.OTP;

/**
 * @author SUNIL NEKKANTI
 *
 */
public interface OTPService {

	String generateOTP(byte[] secretcode, long dynamicNumber, int otpLength,
			boolean checksumFlag, int terminalOffset)
			throws NoSuchAlgorithmException, InvalidKeyException;

	int computeChecksum(long checksumNumber, int significantDigits)
			throws NoSuchAlgorithmException, InvalidKeyException;

	byte[] hmac_sha512(byte[] hmacbytes, byte[] authenticateText)
			throws NoSuchAlgorithmException, InvalidKeyException;

	OTP getOTPByCustomerIDAndType(String customerId, String type);

	public OTP getOTP(String id);

	boolean isOTPVerified(OTP dbOTP, String otp, String transactionId,
			String type);

}
