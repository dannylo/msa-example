package com.msaexample.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.product.domain.Customer;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<Customer> save(@RequestBody Customer customer){
		return new ResponseEntity<Customer>(this.customerService.save(customer), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAll(){
		return new ResponseEntity<List<Customer>>(this.customerService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Customer>(this.customerService.getById(id), HttpStatus.OK);
		} catch (CustomerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Customer customer){
		try {
			return new ResponseEntity<Customer>(this.customerService.update(customer), HttpStatus.OK);
		} catch (CustomerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
