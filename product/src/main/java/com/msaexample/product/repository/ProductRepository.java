package com.msaexample.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
