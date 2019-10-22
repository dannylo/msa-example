package com.msaexample.inventory.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.domain.Transaction;
import com.msaexample.inventory.exceptions.InventoryException;
import com.msaexample.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventories")
public class TransactionController {

	@Autowired
	private InventoryService inventoryService;
	
	@PostMapping("/{idProduct}/transaction")
	public ResponseEntity<?> registerTransactions(@RequestBody Transaction transaction, @PathVariable("idProduct") int idProduct) {
		try {
			return new ResponseEntity<Inventory>(this.inventoryService.createTransaction(transaction, idProduct), HttpStatus.OK) ;
		} catch (InventoryException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
