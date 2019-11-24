package com.msaexample.product.dto;

public class CreditResponseDTO {
	
	private long authorizationCode;

	private int status;

	private long operationId;

	public long getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(long authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}
	
	
}
