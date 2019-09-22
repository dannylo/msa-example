package com.msaexample.product.exception;

import com.msaexample.product.enums.ExceptionMessages;

public class ProductException extends Exception {

	private ExceptionMessages message;
	
	public ProductException(ExceptionMessages message) {
		super(message.getDescrition());
		this.message = message;
	}
	
}
