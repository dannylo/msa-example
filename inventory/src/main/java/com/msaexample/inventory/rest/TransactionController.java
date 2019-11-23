package com.msaexample.inventory.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.domain.Transaction;
import com.msaexample.inventory.dto.BundleDTO;
import com.msaexample.inventory.dto.OrderTransanction;
import com.msaexample.inventory.exceptions.InventoryException;
import com.msaexample.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventories")
public class TransactionController {

	@Autowired
	private InventoryService inventoryService;
	
	private Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@PostMapping("/transaction")
	public ResponseEntity<?> registerTransactions(@RequestBody List<OrderTransanction> bundle) {
		try {
			return new ResponseEntity<BundleDTO>(this.inventoryService.createTransaction(bundle), HttpStatus.OK) ;
		} catch (InventoryException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
