package com.msaexample.product.validation.product;

import com.msaexample.product.domain.Product;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.validation.BusinessRule;

public class ProductUnitPriceNullRule implements BusinessRule {

	private Product product;
	
	public ProductUnitPriceNullRule(Product product) {
		this.product = product;
	}
	@Override
	public Boolean check() {
		return product.getUnitPrice() == null;
	}
	@Override
	public Exception getException() {
		return new ProductException(ExceptionMessages.PRODUCTS_INVALID);
	}

}
