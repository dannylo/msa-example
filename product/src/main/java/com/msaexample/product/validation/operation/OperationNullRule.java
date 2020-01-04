package com.msaexample.product.validation.operation;

import org.springframework.util.ObjectUtils;

import com.msaexample.product.domain.Operation;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.OperationException;
import com.msaexample.product.validation.BusinessRule;

public class OperationNullRule implements BusinessRule {
	
	private Operation operation;
	
	public OperationNullRule(Operation operation) {
		this.operation = operation;
	}

	@Override
	public Boolean check() {
		return ObjectUtils.isEmpty(operation);
	}

	@Override
	public Exception getException() {
		return new OperationException(ExceptionMessages.OPERATION_INVALID_PROCESS);
	}

}
