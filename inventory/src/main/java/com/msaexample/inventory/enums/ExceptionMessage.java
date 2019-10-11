package com.msaexample.inventory.enums;

public enum ExceptionMessage {
	
	INVENTORY_NOT_FOUND ("Inventory not found."),
	INVENTORY_QUANTITY_INVALID ("Invalid quantity for this transation. Insufficient inventory."),
	INVENTORY_PRODUCT_INVALID("Invalid product for this request.");
	
	private String descrition;
	
	ExceptionMessage(String description){
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
