package com.msaexample.product.enums;

public enum ExceptionMessages {

	PRODUCTS_NOT_FOUND ("Products not found");
	
	private String descrition;
	
	ExceptionMessages(String description){
		this.descrition = description;
	}

	public String getDescrition() {
		return descrition;
	}
	
	@Override
	public String toString() {
		return getDescrition();
	}
}
