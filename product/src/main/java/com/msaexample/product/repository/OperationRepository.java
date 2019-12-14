package com.msaexample.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.product.domain.Customer;
import com.msaexample.product.domain.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

	List<Operation> findByCustomer(Customer customer);
}
