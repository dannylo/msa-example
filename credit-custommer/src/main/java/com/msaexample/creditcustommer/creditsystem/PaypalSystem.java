package com.msaexample.creditcustommer.creditsystem;

import com.msaexample.creditcustommer.dto.CustomerData;

public class PaypalSystem implements CreditSystem {
	
	@Override
	public String execute(CustomerData data) {
		//TODO Paypal payment implementation.
		
		return "Status: OK";
	}

}
