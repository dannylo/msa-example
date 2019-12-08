package com.msaexample.creditcustommer.dto;

import java.math.BigDecimal;

import com.msaexample.creditcustommer.creditsystem.CreditResponse;
import com.msaexample.creditcustommer.enums.PaymentSystem;

public class CustomerData {
	
	private PaymentSystem paymentSystem;
	private long operationId;
	private CreditCard creditCard;
	private BigDecimal valueRequested;
	private int result;
	
	
	public CreditResponse performPayment() {
		return this.paymentSystem.executeOperation(this);
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

	public PaymentSystem getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(PaymentSystem paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	

}
