package com.msaexample.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	
	
	@PostMapping("/sale")
	public ResponseEntity<?> createSaleTransaction(@RequestBody List<TransactionDTO> transactions) {
		try {
			this.service.createSale(transactions);
			return ResponseEntity.ok().body("Sale succeffuly created.");
		} catch (ProductException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/buy")
	public ResponseEntity<?> createBuyTransaction(@RequestBody List<TransactionDTO> transactions) {
		try {
			this.service.createBuy(transactions);
			return ResponseEntity.ok().body("Buy succeffuly created.");
		} catch (ProductException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
