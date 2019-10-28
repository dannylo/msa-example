package com.msaexample.product.validation.product;

import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;

import com.msaexample.product.domain.Product;
import com.msaexample.product.validation.AbstractValidationMediator;
import com.msaexample.product.validation.BusinessInvalidation;
import com.msaexample.product.validation.BusinessInvalidationImpl;

public class ProductValidationMediator extends AbstractValidationMediator<Product> {

	public ProductValidationMediator() {
		super(new BusinessInvalidationImpl());
	}

	@Override
	public BusinessInvalidation verify(Product product) {
		BusinessInvalidation invalidations = this.getBusinessInvalidation();
		
		    invalidations
	    		.withRule(new ProductUnitPriceNullRule(product))
		    	.withRule(new ProductInvalidUnitPriceRule(product))
		    	.withRule(new ProductNameEmptyRule(product))
		    	.exists();
		
		return invalidations;
	}

}
