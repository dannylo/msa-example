package com.msaexample.creditcustommer.creditsystem;

import com.msaexample.creditcustommer.dto.CustomerData;

public class PagSeguroSystem implements CreditSystem{

	@Override
	public CreditResponse execute(CustomerData data) {
		try {
			Thread.sleep(5000);
			String json = "{\"status\": \"OK\", \"auth\": 344322245554 }";
			CreditResponse response = new PagSeguroResponse(json);
			return response;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
}
