package com.msaexample.product.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Request;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.OperationService;
import com.msaexample.product.servicerequest.TransactionServiceRequest;

@RestController
@RequestMapping("/operations")
public class OperationController {
	
	@Autowired
	private OperationService service;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Operation operation) {
		try {
			ResponseEntity<Operation> response = new ResponseEntity<Operation>(this.service.save(operation), HttpStatus.OK);
			return response;
		} catch (ProductException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InventoryApiException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (JsonParseException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (CustomerException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
