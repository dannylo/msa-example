package com.msaexample.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.inventory.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	public Inventory findByIdProduct(int idProduct);
}
