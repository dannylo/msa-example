package com.msaexample.product.dto;

import java.util.List;

public class BundleTransactionDTO {
	
	private String operation;
	private List<TransactionDTO> transactions;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

}
