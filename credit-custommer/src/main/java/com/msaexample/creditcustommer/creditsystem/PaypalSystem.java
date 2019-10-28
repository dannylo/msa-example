package com.msaexample.creditcustommer.creditsystem;

import com.msaexample.creditcustommer.dto.CustomerData;

public class PaypalSystem implements CreditSystem {
	
	@Override
	public String execute(CustomerData data) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//simulated response.
		return "{ Status: OK, PaypalID: 32384349958 } ";
	}

}
