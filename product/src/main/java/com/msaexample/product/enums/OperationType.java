package com.msaexample.product.enums;

public enum OperationType {
	
	SALE("Sale");
	
	private String description;
	
	private OperationType(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
