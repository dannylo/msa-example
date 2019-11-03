package com.msaexample.inventory.dto;

import java.util.List;

import com.msaexample.inventory.domain.Transaction;
import com.msaexample.inventory.enums.TypeTransaction;

public class BundleDTO {

	private TypeTransaction operation;
	private List<Transaction> transactions;
	
	public BundleDTO(TypeTransaction operation, List<Transaction> transactions) {
		this.operation = operation;
		this.transactions = transactions;
	}

	public TypeTransaction getOperation() {
		return operation;
	}

	public void setOperation(TypeTransaction operation) {
		this.operation = operation;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
