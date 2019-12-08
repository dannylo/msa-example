package com.msaexample.product.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.msaexample.product.enums.OperationType;
import com.msaexample.product.enums.StatusOperation;

@Entity
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Enumerated
	private OperationType type;
	@ManyToOne
	private Customer customer;
	@OneToMany(cascade = CascadeType.PERSIST)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Request> requests;
	private BigDecimal total;
	@Enumerated(EnumType.STRING)
	private StatusOperation status;
	
	public Operation() {
		setStatus(StatusOperation.PENDENT);
		setTotal(BigDecimal.ZERO);
	}
	
	public void calculateTotal() {
		this.requests.stream()
		.forEach(r -> total = total.add(r.getTotal()));
	}

	public long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public StatusOperation getStatus() {
		return status;
	}

	public void setStatus(StatusOperation status) {
		this.status = status;
	}

	
	
	

}
