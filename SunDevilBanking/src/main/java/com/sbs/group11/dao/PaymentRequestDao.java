package com.sbs.group11.dao;

import java.util.List;

import com.sbs.group11.model.PaymentRequest;

public interface PaymentRequestDao {

	List<PaymentRequest> getPaymentsByAccNumber(String accNumber,
			int initiatedBy);

	void savePaymentRequest(PaymentRequest payement);
	
	PaymentRequest getPaymentRequest(int id);

}
