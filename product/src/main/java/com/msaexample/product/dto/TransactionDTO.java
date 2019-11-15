package com.msaexample.product.dto;

import java.math.BigDecimal;

public class TransactionDTO {

	private int productId;
	private int qtd;
	private BigDecimal total;
	
	public TransactionDTO(int productId, int qtd) {
		this.productId = productId;
		this.qtd = qtd;
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
