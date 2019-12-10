package com.msaexample.product.enums;

public enum ExceptionMessages {

	PRODUCTS_NOT_FOUND ("Product not found"),
	PRODUCTS_UNAVAILABLE ("Product is Unavailable"),
	PRODUCTS_INVALID ("Product is invalid for this operation."),
	
	INVENTORY_SERVER_ERROR ("An error ocurred in inventory API comunication."),
	
	CUSTOMERS_NOT_FOUND ("Customer not found"),
	
	OPERATION_NOT_FOUND ("Operation not found"),
	
	OPERATION_INVALID_PROCESS ("Operation not used for this process.");
	
	
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
