package com.msaexample.product.validation;

import java.util.ArrayList;
import java.util.List;

public class BusinessInvalidationImpl implements BusinessInvalidation {

	private List<BusinessRule> rules;
	
	public BusinessInvalidationImpl() {
		this.rules = new ArrayList<>();
	}
	
	public BusinessInvalidationImpl(BusinessRule rule) {
		this.rules = new ArrayList<>();
		withRule(rule);
	}
	
	@Override	
	public boolean exists() {
		return this.rules.stream()
			.anyMatch(r -> r.check());
	}

	@Override
	public BusinessInvalidation withRule(BusinessRule rule) {
		this.rules.add(rule);
		return this;
	}

}
