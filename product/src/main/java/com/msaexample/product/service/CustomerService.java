package com.msaexample.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.msaexample.product.domain.Customer;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repository;
	
	public Customer save(Customer customer) {
		return this.repository.save(customer);
	}
	
	public List<Customer> getAll(){
		return this.repository.findAll();
	}
	
	public Customer getByEmail(String email) throws CustomerException {
		Customer customer = this.repository.findByEmail(email);
		if(ObjectUtils.isEmpty(customer)) {
			throw new CustomerException(ExceptionMessages.CUSTOMERS_NOT_FOUND);
		}
		return customer;
	}
	
	public Customer getById(int id) throws CustomerException {
		Optional<Customer> finded = this.repository.findById(id);
		if(!finded.isPresent()) {
			throw new CustomerException(ExceptionMessages.CUSTOMERS_NOT_FOUND);
		}
		
		return finded.get();
	}

	public Customer update(Customer customer) throws CustomerException {
		this.getById(customer.getId());
		return this.save(customer);
	}
}
