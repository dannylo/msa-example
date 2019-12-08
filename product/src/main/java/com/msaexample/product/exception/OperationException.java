package com.msaexample.product.exception;

import com.msaexample.product.enums.ExceptionMessages;

public class OperationException extends Exception {
	
	private ExceptionMessages message;

	public OperationException(ExceptionMessages message) {
		super(message.getDescrition());
		this.message = message;
	}
}
