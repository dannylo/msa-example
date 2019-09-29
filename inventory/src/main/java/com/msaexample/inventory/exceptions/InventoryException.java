package com.msaexample.inventory.exceptions;

import com.msaexample.inventory.enums.ExceptionMessage;

public class InventoryException extends Exception {
	
	private ExceptionMessage exceptionMessage;
	
	public InventoryException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage.getDescrition());
		this.exceptionMessage = exceptionMessage;
	}

}
