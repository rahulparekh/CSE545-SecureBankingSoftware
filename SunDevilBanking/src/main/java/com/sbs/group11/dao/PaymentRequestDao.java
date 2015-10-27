package com.sbs.group11.dao;

import com.sbs.group11.model.PaymentRequest;

public interface PaymentRequestDao {
	
	void getPaymentsByAccNumber(String accNumber);
	void savePaymentRequest(PaymentRequest payement);

}
