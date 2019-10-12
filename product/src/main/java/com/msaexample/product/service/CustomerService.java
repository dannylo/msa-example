package com.msaexample.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Customer getById(int id) throws CustomerException {
		Optional<Customer> finded = this.repository.findById(id);
		if(!finded.isPresent()) {
			throw new CustomerException(ExceptionMessages.CUSTOMERS_NOT_FOUND);
		}
		
		return finded.get();
	}

	public Customer update(Customer customer) throws CustomerException {
		Customer verify = this.getById(customer.getId());
		return this.save(customer);
	}
}
