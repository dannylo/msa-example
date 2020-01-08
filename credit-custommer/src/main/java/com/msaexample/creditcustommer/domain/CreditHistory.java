package com.msaexample.creditcustommer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.msaexample.creditcustommer.enums.Status;

@Entity
@Table(name = "credit_history")
public class CreditHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	@Column(name = "processing_date", nullable = false)
	public LocalDate processingDate;
	@Column(name = "operation_id")
	public long operationId;
	private BigDecimal value;
	@Enumerated
	private Status status;
	@Column(name = "auth")
	private long authorizationCode;
	
	public CreditHistory() {
		this.processingDate = LocalDate.now();
	}
	
	public CreditHistory(long operationId, BigDecimal value, Status status, long authorizationCode) {
		this.operationId = operationId;
		this.value = value;
		this.status = status;
		this.authorizationCode = authorizationCode;
		this.processingDate = LocalDate.now();
	}

	public long getId() {
		return id;
	}

	public LocalDate getProcessingDate() {
		return processingDate;
	}
	
	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(long authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	

}
