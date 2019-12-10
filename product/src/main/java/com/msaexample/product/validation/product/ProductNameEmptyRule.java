package com.msaexample.product.validation.product;


import org.springframework.util.StringUtils;

import com.msaexample.product.domain.Product;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.validation.BusinessRule;

public class ProductNameEmptyRule implements BusinessRule {

	private Product product;
	
	public ProductNameEmptyRule(Product product) {
		this.product = product;
	}
	@Override
	public Boolean check() {
		return StringUtils.isEmpty(product.getName());
	}
	@Override
	public Exception getException() {
		return new ProductException(ExceptionMessages.PRODUCTS_INVALID);
	}

}
