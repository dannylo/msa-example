package com.msaexample.inventory.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.exceptions.InventoryException;
import com.msaexample.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService service;

	@PostMapping
	public ResponseEntity<Inventory> save(@RequestBody Inventory inventory){
		return new ResponseEntity<Inventory> (this.service.save(inventory), HttpStatus.OK);
	}
	

	@GetMapping("/{idProduct}/product")
	public ResponseEntity<?> findByProductId(@PathVariable("idProduct") int idProduct){
		try {
			return new ResponseEntity<Inventory> (this.service.getByProduct(idProduct), HttpStatus.OK);
		} catch (InventoryException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{idProduct}/input")
	public ResponseEntity<?> registerInput(@PathVariable("idProduct") int idProduct, @RequestParam("qtd") int qtd){
		try {
			Inventory inventory = this.service.getByProduct(idProduct);
			inventory.increase(qtd);
			inventory = this.service.save(inventory);
			return new ResponseEntity<Inventory> (inventory, HttpStatus.OK);
		} catch (InventoryException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{idProduct}/output")
	public ResponseEntity<?> registerOutput(@PathVariable("idProduct") int idProduct, @RequestParam("qtd") int qtd){
		try {
			Inventory inventory = this.service.getByProduct(idProduct);
			inventory.increase(qtd);
			inventory = this.service.save(inventory);
			return new ResponseEntity<Inventory> (inventory, HttpStatus.OK);
		} catch (InventoryException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
