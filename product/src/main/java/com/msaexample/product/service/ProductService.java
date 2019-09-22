package com.msaexample.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msaexample.product.domain.Product;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public Product save(Product newProduct) {
		return this.repository.save(newProduct);
	}
	
	public List<Product> getAll() {
		return this.repository.findAll();
	}
	
	public Product getById(int id) throws ProductException {
		Optional<Product> opt = this.repository.findById(id);
		if(!opt.isPresent()) {
			throw new ProductException(ExceptionMessages.PRODUCTS_NOT_FOUND);
		}
		
		return opt.get();
	}

}
