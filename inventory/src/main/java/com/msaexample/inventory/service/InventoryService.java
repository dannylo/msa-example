package com.msaexample.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.enums.ExceptionMessage;
import com.msaexample.inventory.exceptions.InventoryException;
import com.msaexample.inventory.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	public Inventory save(Inventory inventory) {
		return repository.save(inventory);
	}

	public Inventory getByProduct(int idProduct) throws InventoryException {
		if (idProduct == 0) {
			throw new InventoryException(ExceptionMessage.INVENTORY_PRODUCT_INVALID);
		}
		Inventory finded = repository.findByIdProduct(idProduct);
		if (finded == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		}

		return finded;
	}

	public Inventory increaseInventory(int idProduct, int qtdProduct) throws InventoryException {
		Inventory inventory = this.getByProduct(idProduct);
		if (inventory == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		}

		inventory.increase(qtdProduct);
		return this.save(inventory);
	}

	public Inventory decreaseInventory(int idProduct, int qtdProduct) throws InventoryException {
		Inventory inventory = this.getByProduct(idProduct);
		if (inventory == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		}
		inventory.decrease(qtdProduct);
		return this.save(inventory);
	}

}
