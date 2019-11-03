package com.msaexample.inventory.dto;

import java.math.BigDecimal;

import com.msaexample.inventory.domain.Transaction;

public class OrderTransanction {
	private int product;
	private int qtd;
	private BigDecimal total;
	
	
	public Transaction convert() {
		Transaction transaction = new Transaction();
		transaction.setQtd(this.qtd);
		
		return transaction;
	}
	

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
