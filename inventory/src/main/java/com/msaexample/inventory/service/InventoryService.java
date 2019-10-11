package com.msaexample.inventory.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.domain.Transaction;
import com.msaexample.inventory.enums.ExceptionMessage;
import com.msaexample.inventory.enums.TypeTransaction;
import com.msaexample.inventory.exceptions.InventoryException;
import com.msaexample.inventory.repository.InventoryRepository;
import com.msaexample.inventory.repository.TransactionRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private TransactionRepository transactionRepository;

	public Inventory save(Inventory inventory) {
		inventory.setLastUpdated(LocalDate.now());
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

	@Transactional
	public Inventory increaseInventory(Transaction transaction, int idProduct) throws InventoryException {
		Inventory inventory = transaction.getInventory();

		transaction.setType(TypeTransaction.BUY);
		transaction.setDate(LocalDate.now());

		if (inventory == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		}

		transaction.setInventory(inventory);
		inventory.increase(transaction.getQtd());
		transactionRepository.save(transaction);

		return this.save(inventory);
	}

	@Transactional
	public Inventory decreaseInventory(Transaction transaction, int idProduct) throws InventoryException {
		Inventory inventory = getByProduct(idProduct);

		transaction.setType(TypeTransaction.SALE);
		transaction.setDate(LocalDate.now());

		if (inventory == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		} else if (inventory.getQtdAvailable() < transaction.getQtd()) {
			throw new InventoryException(ExceptionMessage.INVENTORY_QUANTITY_INVALID);
		}

		transaction.setInventory(inventory);
		transactionRepository.save(transaction);

		inventory.decrease(transaction.getQtd());
		return this.save(inventory);

	}

}
