package com.msaexample.product.validation;

public abstract class AbstractValidationMediator <E> implements ValidationMediator<E> {

	protected BusinessInvalidation businessInvalidation;
	
	public AbstractValidationMediator(BusinessInvalidation businessInvalidation) {
		this.businessInvalidation = businessInvalidation;
	}

	public BusinessInvalidation getBusinessInvalidation() {
		return businessInvalidation;
	}
	
	
}
