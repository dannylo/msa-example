package com.msaexample.creditcustommer.creditsystem;

import com.msaexample.creditcustommer.dto.CustomerData;

public interface CreditSystem {
	
	CreditResponse execute(CustomerData data);
	
}
