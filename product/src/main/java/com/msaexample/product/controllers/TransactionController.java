package com.msaexample.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.dto.BundleTransactionDTO;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	
	@PostMapping
	public ResponseEntity<?> createTransaction(@RequestBody List<TransactionDTO> transactions) {
		try {
			ResponseEntity<BundleTransactionDTO> response = new ResponseEntity<BundleTransactionDTO>(this.service.sendTransactions(transactions), HttpStatus.OK);
			return response;
		} catch (ProductException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InventoryApiException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
