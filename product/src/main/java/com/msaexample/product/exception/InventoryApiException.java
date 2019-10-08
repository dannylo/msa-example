package com.msaexample.product.exception;

import java.io.IOException;

import com.msaexample.product.enums.ExceptionMessages;

public class InventoryApiException extends IOException{
	
	private ExceptionMessages message;
	
	public InventoryApiException(ExceptionMessages message) {
		super(message.getDescrition());
		this.message = message;
	}
	
	public InventoryApiException(String message) {
		super(message);
	}
}
