package com.msaexample.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msaexample.inventory.domain.Inventory;
import com.msaexample.inventory.domain.Transaction;
import com.msaexample.inventory.dto.BundleDTO;
import com.msaexample.inventory.dto.OrderTransanction;
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
	public BundleDTO createTransaction(List<OrderTransanction> bundle) throws InventoryException {
		List<Transaction> transactions = new ArrayList<>();

		bundle.forEach(order -> {
			Transaction transaction = order.convert();
			transaction.setDate(LocalDate.now());
			transaction.setInventory(this.getByProduct(order.getProductId()));
			this.prepareTransaction(transaction);
			this.save(transaction.getInventory());
			transactions.add(transaction);
		});
		
		return new BundleDTO(transactions.stream().findFirst().get().getType(), LocalDate.now());
	}

	private void prepareTransaction(Transaction transaction) throws InventoryException {
		if (transaction.getInventory() == null) {
			throw new InventoryException(ExceptionMessage.INVENTORY_NOT_FOUND);
		}

		if (transaction.getQtd() < 0) {
			// normalizando quantidade.
			transaction.setQtd(transaction.getQtd() * -1);
			if (transaction.getInventory().getQtdAvailable() < transaction.getQtd()) {
				throw new InventoryException(ExceptionMessage.INVENTORY_QUANTITY_INVALID);
			}
			transaction.setType(TypeTransaction.DECREASE);
			transaction.getInventory().decrease(transaction.getQtd());
		} else {
			transaction.setType(TypeTransaction.INCREASE);
			transaction.getInventory().increase(transaction.getQtd());
		}

		transaction.getInventory().setLastUpdated(LocalDate.now());
		transactionRepository.save(transaction);
	}

}
