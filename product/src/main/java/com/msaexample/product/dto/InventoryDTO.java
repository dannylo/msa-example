package com.msaexample.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

public class InventoryDTO implements Serializable {
	
	private long qtdAvailable;
	private int  idProduct;
	private BigDecimal averageUnitPrice;
	private BigDecimal total;

	public long getQtdAvailable() {
		return qtdAvailable;
	}

	public void setQtdAvailable(long qtdAvailable) {
		this.qtdAvailable = qtdAvailable;
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public BigDecimal getAverageUnitPrice() {
		return averageUnitPrice;
	}

	public void setAverageUnitPrice(BigDecimal averageUnitPrice) {
		this.averageUnitPrice = averageUnitPrice;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	

}
