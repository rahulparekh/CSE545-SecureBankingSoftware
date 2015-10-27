package com.sbs.group11.dao;

import org.springframework.stereotype.Repository;

import com.sbs.group11.model.OTP;

@Repository("OTPDao")
public class OTPDao extends AbstractDao<Integer, OTP> {
	
	public OTP getOTPByCustomerIDAndType(String customerId, String type) {
		
		OTP otp = (OTP) getSession()
				.createQuery("from OTP where CustomerId = :customerId and type = :type and OTPExpiry >= NOW() ORDER BY id DESC")
				.setParameter("customerId", customerId)
				.setParameter("type", type)
				.setMaxResults(1)
				.uniqueResult();
		
		return otp;
	}
	
	public OTP getOTPByID(String otpID) {
		
		try {
			return (OTP) getSession().get(OTP.class, Integer.valueOf(otpID));
		} catch (Exception e) {
			return null;
		}
		
	}
		
}
