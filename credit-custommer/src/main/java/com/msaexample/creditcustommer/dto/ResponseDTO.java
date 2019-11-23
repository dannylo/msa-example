package com.msaexample.creditcustommer.dto;

import com.msaexample.creditcustommer.enums.Status;

public class ResponseDTO {

	private long authorizationCode;

	private Status status;

	private long operationId;
	
	public ResponseDTO(long authorizationCode, Status status, long operationId) {
		this.authorizationCode = authorizationCode;
		this.status = status;
		this.operationId = operationId;
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

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

}
