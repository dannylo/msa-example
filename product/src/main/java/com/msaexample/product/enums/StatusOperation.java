package com.msaexample.product.enums;

public enum StatusOperation {
	
	ACCEPT("ACCEPT"),
	PENDENT("PENDENT"),
	DENIED("DEINED");
	
	private String description;
	
	private StatusOperation(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
