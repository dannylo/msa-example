package com.msaexample.product.validation;

public interface BusinessRule {
	
	Boolean check();
	
	Exception getException();

}
