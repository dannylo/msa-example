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
	@Column(nullable = false)
	private int idCustomer;
	private BigDecimal value;
	@Enumerated
	private Status status;
	
	public CreditHistory() {
		this.processingDate = LocalDate.now();
	}

	public long getId() {
		return id;
	}

	public LocalDate getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(LocalDate processingDate) {
		this.processingDate = processingDate;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
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
	
	

}
