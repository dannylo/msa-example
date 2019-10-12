package com.msaexample.product.enums;

public enum ExceptionMessages {

	PRODUCTS_NOT_FOUND ("Product not found"),
	PRODUCTS_INVALID ("Product is invalid for this operation."),
	
	INVENTORY_SERVER_ERROR ("An error ocurred in inventory API comunication."),
	
	CUSTOMERS_NOT_FOUND ("Customer not found");
	
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
