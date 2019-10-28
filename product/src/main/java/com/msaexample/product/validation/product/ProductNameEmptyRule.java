package com.msaexample.product.validation.product;


import org.springframework.util.StringUtils;

import com.msaexample.product.domain.Product;
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

}
