package com.sbs.group11.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sbs.group11.model.PaymentRequest;

@Repository("PaymentRequestDao")
public class PaymentRequestDaoImpl  extends AbstractDao<Integer, PaymentRequest> implements PaymentRequestDao{

	public void savePaymentRequest(PaymentRequest payment) {
		
		getSession().saveOrUpdate(payment);
	}

	@SuppressWarnings("unchecked")
	public List<PaymentRequest> getPaymentsByAccNumber(String accNumber, int initiatedBy) {
		
		String query = "";
		if (initiatedBy == 0) {
			query = "from PaymentRequest where MerchantAccNumber = :accNumber and InitiatedBy = 0";
		} else {
			query = "from PaymentRequest where CustomerAccNumber = :accNumber and InitiatedBy = 1";
		}	
		
		List<PaymentRequest> requests = getSession()
			.createQuery(query)
			.setParameter("accNumber", accNumber)
			.list();				
		
		return requests;
	}

}
