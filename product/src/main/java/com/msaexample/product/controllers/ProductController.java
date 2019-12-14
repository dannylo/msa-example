package com.msaexample.product.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.domain.Product;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping
	public ResponseEntity<List<Product>> getAll(){
		return new ResponseEntity<List<Product>>(this.service.getAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Product product){
		try {
			return new ResponseEntity<Product> (this.service.save(product), HttpStatus.OK);
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Product>(this.service.getById(id), HttpStatus.OK);
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Product product){
		try {
			return new ResponseEntity<Product> (this.service.updated(product), HttpStatus.OK);
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> removeById(@RequestBody Product product){
		try {
			this.service.remove(product);
			return ResponseEntity.ok().body("Product was removed.");
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	

}
