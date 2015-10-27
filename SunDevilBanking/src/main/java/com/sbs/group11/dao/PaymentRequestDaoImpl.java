package com.sbs.group11.dao;

import org.springframework.stereotype.Repository;

import com.sbs.group11.model.PaymentRequest;

@Repository("PaymentRequestDao")
public class PaymentRequestDaoImpl  extends AbstractDao<Integer, PaymentRequest> implements PaymentRequestDao{

	public void savePaymentRequest(PaymentRequest payment) {
		
		getSession().saveOrUpdate(payment);
	}

	public void getPaymentsByAccNumber(String accNumber) {
		// TODO Auto-generated method stub
		
	}

}
