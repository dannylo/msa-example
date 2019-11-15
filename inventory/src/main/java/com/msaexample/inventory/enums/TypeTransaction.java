package com.msaexample.inventory.enums;

public enum TypeTransaction {
	
	DECREASE("Decrease"),
	INCREASE("Increase");
	
	private String type;
	
	private TypeTransaction(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
