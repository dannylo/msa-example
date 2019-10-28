package com.msaexample.product.validation.product;

import java.math.BigDecimal;

import com.msaexample.product.domain.Product;
import com.msaexample.product.validation.BusinessRule;

public class ProductInvalidUnitPriceRule implements BusinessRule {

	private Product product;
	
	public ProductInvalidUnitPriceRule(Product product) {
		this.product = product;
	}
	@Override
	public Boolean check() {
		return (product
				.getUnitPrice()
				.equals(BigDecimal.ZERO) || 
				product
				.getUnitPrice()
				.compareTo(BigDecimal.ZERO) == -1);
	}

}
