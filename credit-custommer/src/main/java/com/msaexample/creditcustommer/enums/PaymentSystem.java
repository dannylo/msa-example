package com.msaexample.creditcustommer.enums;

import com.msaexample.creditcustommer.creditsystem.*;
import com.msaexample.creditcustommer.dto.CustomerData;

public enum PaymentSystem {

	PAGSEGURO(1, new PagSeguroSystem()),
	PAYPAL(2, new PaypalSystem());
	
   PaymentSystem(int code, CreditSystem creditSystem) {
		this.creditSystem = creditSystem;
	}
	
	private CreditSystem creditSystem;
	private int code;
	
	public String executeOperation(CustomerData data) {
		return this.creditSystem.execute(data);
	}

}
