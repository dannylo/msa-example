package com.msaexample.creditcustommer.dto;

import java.math.BigDecimal;

import com.msaexample.creditcustommer.enums.PaymentSystem;

public class CustomerData {
	
	private PaymentSystem paymentSystem;
	private String cardNumber;
	private int securityCode;
	private String date;
	private BigDecimal value;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public PaymentSystem getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(PaymentSystem paymentSystem) {
		this.paymentSystem = paymentSystem;
	}
	
	

}
