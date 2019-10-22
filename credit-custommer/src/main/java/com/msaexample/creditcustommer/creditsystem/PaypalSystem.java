package com.msaexample.creditcustommer.creditsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.service.CreditHistoryService;

@Component
public class PaypalSystem implements ICreditSystem {
	
	@Autowired
	private CreditHistoryService creditHistoryService;

	@Override
	public String execute() {
		//TODO: implementar integração com sistema Paypal.
		return null;
	}

}
