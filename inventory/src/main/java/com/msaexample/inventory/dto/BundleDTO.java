package com.msaexample.inventory.dto;

import java.time.LocalDate;

import com.msaexample.inventory.enums.TypeTransaction;

public class BundleDTO {

	private TypeTransaction operation;
	private LocalDate update;
	
	public BundleDTO(TypeTransaction operation, LocalDate update) {
		this.update = update;
		this.operation = operation;
	}

	public TypeTransaction getOperation() {
		return operation;
	}

	public void setOperation(TypeTransaction operation) {
		this.operation = operation;
	}

	public LocalDate getUpdate() {
		return update;
	}

	public void setUpdate(LocalDate update) {
		this.update = update;
	}

	

}
