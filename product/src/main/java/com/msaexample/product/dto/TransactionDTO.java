package com.msaexample.product.dto;

import java.math.BigDecimal;

import com.msaexample.product.domain.Product;

public class TransactionDTO {
	
	private int product;
	private int qtd;
	private BigDecimal total;

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
