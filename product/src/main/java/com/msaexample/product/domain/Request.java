package com.msaexample.product.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Request {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Product product;
	private int qtd;
	private BigDecimal total;
	
	public void calculateTotal() {
		if(qtd < 0) {
			this.total = (getProduct().getUnitPrice().multiply(new BigDecimal(getQtd() * -1)));
		} else {
			this.total = (getProduct().getUnitPrice().multiply(new BigDecimal(getQtd())));
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
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


}
