package com.msaexample.product.exception;

import com.msaexample.product.enums.ExceptionMessages;

public class ProductException extends Exception {

	private ExceptionMessages messageException;
	
	public ProductException(ExceptionMessages message) {
		super(message.getDescrition());
		this.messageException = message;
	}

	public ExceptionMessages getMessageException() {
		return messageException;
	}
	
	
	
}
