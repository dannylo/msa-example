package com.msaexample.inventory.enums;

public enum TypeTransaction {
	
	SALE("Sale"),
	BUY("Buy");
	
	private String type;
	
	private TypeTransaction(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
