package com.msaexample.product.validation.operation;

import com.msaexample.product.domain.Operation;
import com.msaexample.product.validation.AbstractValidationMediator;
import com.msaexample.product.validation.BusinessInvalidation;
import com.msaexample.product.validation.BusinessInvalidationImpl;

public class OperationValidationMediator extends AbstractValidationMediator<Operation> {
	
	public OperationValidationMediator() {
		super(new BusinessInvalidationImpl());
	}

	@Override
	public BusinessInvalidation verify(Operation operation) {
		
		return this.getBusinessInvalidation()
				.withRule(new OperationNullRule(operation));
	}

}
