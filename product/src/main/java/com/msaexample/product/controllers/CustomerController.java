package com.msaexample.product.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@PostMapping
	public ResponseEntity<Customer> save(@RequestBody Customer customer){
		return new ResponseEntity<Customer>(this.customerService.save(customer), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAll(){
		return new ResponseEntity<List<Customer>>(this.customerService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> getCustomerByEmail(@PathParam("email") String email) {
		try {
			return new ResponseEntity<Customer>(this.customerService.getByEmail(email), HttpStatus.OK);
		} catch (CustomerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Customer>(this.customerService.getById(id), HttpStatus.OK);
		} catch (CustomerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Customer customer){
		try {
			return new ResponseEntity<Customer>(this.customerService.update(customer), HttpStatus.OK);
		} catch (CustomerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
