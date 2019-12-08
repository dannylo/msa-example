package com.msaexample.product.dto;

import java.math.BigDecimal;

import com.msaexample.product.domain.CreditCard;

public class CreditMessageDTO {

	private long operationId;
	private CreditCard creditCard;
	private BigDecimal valueRequested;
	
	public CreditMessageDTO(long operationId, CreditCard creditCard, BigDecimal valueRequested) {
		this.operationId = operationId;
		this.creditCard = creditCard;
		this.valueRequested = valueRequested;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public BigDecimal getValueRequested() {
		return valueRequested;
	}

	public void setValueRequested(BigDecimal valueRequested) {
		this.valueRequested = valueRequested;
	}
	
	

}
