package com.msaexample.product.exception;

import com.msaexample.product.enums.ExceptionMessages;

public class CustomerException extends Exception {
	
	private ExceptionMessages message;
	
	public CustomerException(ExceptionMessages message) {
		super(message.getDescrition());
		this.message = message;
	}

}
