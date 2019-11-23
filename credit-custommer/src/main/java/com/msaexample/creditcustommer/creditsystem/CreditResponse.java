package com.msaexample.creditcustommer.creditsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.creditcustommer.enums.Status;

public abstract class CreditResponse {
	
	private ObjectMapper mapper = new ObjectMapper();
	private long authorizationCode;
	private Status status;
	
	public CreditResponse(String rawJson) {
		this.treatResponse(rawJson);
	}

	public abstract void treatResponse(String rawJson);
	
	
	public ObjectMapper getMapper() {
		return mapper;
	}

	public long getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(long authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
