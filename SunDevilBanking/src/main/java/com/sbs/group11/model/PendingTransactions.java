package com.sbs.group11.model;

public class PendingTransactions {

	
	Transaction txn;
	String status;
	
	public PendingTransactions()
	{
		
		
	}
	
	public PendingTransactions(Transaction txn,String status)
	{
		this.txn = txn;
		this.status = status;	
	}

	public Transaction getTxn() {
		return txn;
	}

	public void setTxn(Transaction txn) {
		this.txn = txn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
