package com.msaexample.product.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.domain.Product;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.ProductRepository;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;
import com.msaexample.product.servicerequest.InventoryServiceRequest;
import com.msaexample.product.validation.ValidationMediator;
import com.msaexample.product.validation.product.ProductValidationMediator;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	private RestTemplate restTemplate;

	private ValidationMediator<Product> validator = new ProductValidationMediator();
	
	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;
	
	@Autowired
	private InventoryServiceRequest serviceRequest;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(errorHandler);
	}

	@Transactional
	public Product save(Product newProduct) 
			throws ProductException, JsonParseException, JsonMappingException, IOException {
		if (validator.verify(newProduct).exists()) {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}
		
		newProduct = this.repository.save(newProduct);
		this.serviceRequest.createInventory(newProduct);
		
		return newProduct;
	}


	public List<Product> getAll() {
		return this.repository.findAll();
	}

	public Product getById(int id) throws ProductException {
		Optional<Product> opt = this.repository.findById(id);
		if (!opt.isPresent()) {
			throw new ProductException(ExceptionMessages.PRODUCTS_NOT_FOUND);
		}

		return opt.get();
	}

	public Product updated(Product updatedProduct) throws ProductException {
		// avoiding bad requests.
		this.getById(updatedProduct.getId());
		return this.repository.save(updatedProduct);
	}

	public void remove(Product product) throws ProductException {
		// avoiding bad requests.
		product = this.getById(product.getId());
		if (product.getId() != 0) {
			this.repository.delete(product);
		} else {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}
	}

}
