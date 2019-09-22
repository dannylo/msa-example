package com.msaexample.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.product.domain.Product;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<Product>> getAll(){
		return new ResponseEntity<List<Product>>(this.service.getAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> save(@RequestBody Product product){
		return new ResponseEntity<Product> (this.service.save(product), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Product>(this.service.getById(id), HttpStatus.OK);
		} catch (ProductException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
