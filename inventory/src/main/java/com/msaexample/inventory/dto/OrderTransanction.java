package com.msaexample.inventory.dto;

import java.math.BigDecimal;

import com.msaexample.inventory.domain.Transaction;

public class OrderTransanction {
	
	private int productId;
	private int qtd;
	private BigDecimal total;
	
	
	public Transaction convert() {
		Transaction transaction = new Transaction();
		transaction.setQtd(this.qtd);
		
		return transaction;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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
