package com.msaexample.product.controllers;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.OperationService;

@RestController
@RequestMapping("/operations")
public class OperationController {
	
	@Autowired
	private OperationService service;
	
	private Logger logger = LoggerFactory.getLogger(OperationController.class);
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Operation operation) {
		try {
			ResponseEntity<Operation> response = new ResponseEntity<Operation>(this.service.save(operation), HttpStatus.OK);
			return response;
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InventoryApiException e) {
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
		} catch (CustomerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/customer/{idCustomer}")
	public ResponseEntity<?> getByCustomer(@PathParam("idCustomer") int idCustomer){
		ResponseEntity<List<Operation>> response;
		try {
			response = new ResponseEntity<List<Operation>>(this.service.getOperationsByCustomer(idCustomer), HttpStatus.OK);
			return response;
		} catch (CustomerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
}
