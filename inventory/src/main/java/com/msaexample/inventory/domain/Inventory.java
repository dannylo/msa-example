package com.msaexample.inventory.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "qtd_available")
	private long qtdAvailable;
	@Column(name = "id_product", nullable = false)
	private int  idProduct;
	@Column(name = "average_unit_price")
	private BigDecimal averageUnitPrice;
	private BigDecimal total;
	private LocalDate lastUpdated;
	
	public void increase(int qtdAdded) {
		this.qtdAvailable += qtdAdded;
		this.total = total.add(averageUnitPrice.multiply(new BigDecimal(qtdAdded)));
		this.lastUpdated = LocalDate.now();
	}
	
	public void decrease(int qtdRemoved) {
		this.qtdAvailable -= qtdRemoved;
		this.total = total.subtract(averageUnitPrice.multiply(new BigDecimal(qtdRemoved)));
		this.lastUpdated = LocalDate.now();
	}

	public int getId() {
		return id;
	}	

	public long getQtdAvailable() {
		return qtdAvailable;
	}

	public void setQtdAvailable(long qtdAvailable) {
		this.qtdAvailable = qtdAvailable;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public BigDecimal getAverageUnitPrice() {
		return averageUnitPrice;
	}

	public void setAverageUnitPrice(BigDecimal averageUnitPrice) {
		this.averageUnitPrice = averageUnitPrice;
	}

	public LocalDate getLastUpdated() {
		return lastUpdated;
	}
	
	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
	
	

}
