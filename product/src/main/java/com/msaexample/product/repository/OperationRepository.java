package com.msaexample.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Request;
import com.msaexample.product.service.OperationService;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

}
