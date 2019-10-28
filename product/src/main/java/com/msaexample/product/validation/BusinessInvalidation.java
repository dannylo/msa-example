package com.msaexample.product.validation;

public interface BusinessInvalidation {

	 boolean exists();
	 BusinessInvalidation withRule(BusinessRule rule);
	
}
