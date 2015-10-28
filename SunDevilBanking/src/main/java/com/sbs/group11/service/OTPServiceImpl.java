/**
 * 
 */
package com.sbs.group11.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.group11.dao.OTPDao;
import com.sbs.group11.model.OTP;

/**
 * @author SUNIL NEKKANTI
 *
 *         USAGE : To generate OTP invoke the generateOTP method with its
 *         arguments secretcode - sessionID or shared secret between server and
 *         client (not a constant), dynamicNumber - a dynamic number which
 *         changes on a per use basis like time or counter, otpLength - length
 *         of the OTP which can be 8 digits max, checksumFlag - tells if
 *         checksum needs to be added to OTP, terminalOffset - Offset into MAC
 *         to begin truncation. It needs to be dynamic on a per use basis (can
 *         use the dynamicNumber argument value)
 * 
 *         Example invocation : OTPServiceImpl.generateOTP(
 *         "+&*#(?b^&VHJBHJK064842348rdsgdsg&*(%&**&^&((", 20, 8, false, 20);
 * 
 */
@Transactional
@Service("OTPService")
public class OTPServiceImpl implements OTPService {

	private static final int[] allDigits = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
	private static final int[] EXPONENT = { 1, 10, 100, 1000, 10000, 100000,
			1000000, 10000000, 100000000 };

	@Autowired
	private OTPDao dao;

	@Autowired
	private BCryptHashService hashService;

	public int computeChecksum(long checksumNumber, int significantDigits) {
		boolean doubleDigit = true;
		int total = 0;
		while (0 < significantDigits--) {
			int digit = (int) (checksumNumber % 10);
			checksumNumber /= 10;
			if (doubleDigit) {
				digit = allDigits[digit];
			}
			total += digit;
			doubleDigit = !doubleDigit;
		}
		int result = total % 10;
		if (result > 0) {
			result = 10 - result;
		}
		return result;
	}

	public byte[] hmac_sha512(byte[] hmacbytes, byte[] authenticateText)
			throws NoSuchAlgorithmException, InvalidKeyException {

		Mac hmacSha512;
		try {
			hmacSha512 = Mac.getInstance("HmacSHA512");
		} catch (NoSuchAlgorithmException nsae) {
			hmacSha512 = Mac.getInstance("HMAC-SHA-512");
		}
		SecretKeySpec macKey = new SecretKeySpec(hmacbytes, "RAW");
		hmacSha512.init(macKey);
		return hmacSha512.doFinal(authenticateText);
	}

	public String generateOTP(byte[] secretcode, long dynamicNumber,
			int otpLength, boolean checksumFlag, int terminalOffset)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String result = null;
		int digits = checksumFlag ? (otpLength + 1) : otpLength;
		byte[] text = new byte[8];
		for (int i = text.length - 1; i >= 0; i--) {
			text[i] = (byte) (dynamicNumber & 0xff);
			dynamicNumber >>= 8;
		}

		byte[] hash = hmac_sha512(secretcode, text);

		int offset = hash[hash.length - 1] & 0xf;
		if ((0 <= terminalOffset) && (terminalOffset < (hash.length - 4))) {
			offset = terminalOffset;
		}
		int binary = ((hash[offset] & 0x7f) << 24)
				| ((hash[offset + 1] & 0xff) << 16)
				| ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

		int otp = binary % EXPONENT[otpLength];
		if (checksumFlag) {
			otp = (otp * 10) + computeChecksum(otp, otpLength);
		}
		result = Integer.toString(otp);
		while (result.length() < digits) {
			result = "0" + result;
		}
		return result;
	}

	public OTP getOTPByCustomerIDAndType(String customerId, String type) {
		return dao.getOTPByCustomerIDAndType(customerId, type);
	}
	
	public OTP getOTP(String id) {
		
		if (id == null)
			return null;
		
		return dao.getOTPByID(id);
	}

	public boolean isOTPVerified(OTP dbOTP, String otp,
			String transactionId, String type) {
		if (otp != null && transactionId != null
				&& type != null) {
			
			// check if OPT and type match and whether the transaction id is the
			// same as the request
			if (dbOTP != null && hashService.checkBCryptHash(otp, dbOTP.getOtp())
					&& dbOTP.getType().equalsIgnoreCase(type)
					&& dbOTP.getTransaction().getTransactionID().equals(transactionId)) {
				
				return true;
				
			}
		}
		return false;
	}

}
