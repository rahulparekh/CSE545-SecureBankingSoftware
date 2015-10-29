package com.sbs.group11.model;

public class ModificationTransaction {

	private String senderAccNumber;
	
	private String recieverAccNumber;
	
	private String amount;
	
	private String transactionID;

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getSenderAccNumber() {
		return senderAccNumber;
	}

	public void setSenderAccNumber(String senderAccNumber) {
		this.senderAccNumber = senderAccNumber;
	}

	public String getRecieverAccNumber() {
		return recieverAccNumber;
	}

	public void setRecieverAccNumber(String recieverAccNumber) {
		this.recieverAccNumber = recieverAccNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
