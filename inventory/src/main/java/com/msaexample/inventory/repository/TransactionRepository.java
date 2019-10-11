package com.msaexample.inventory.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.inventory.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {}
