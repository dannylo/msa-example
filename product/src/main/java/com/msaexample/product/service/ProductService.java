package com.msaexample.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Product;
import com.msaexample.product.dto.InventoryDTO;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.ProductRepository;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private InventoryConfig inventoryConfig;

	private RestTemplate restTemplate;

	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(errorHandler);
	}

	@Transactional
	public Product save(Product newProduct) {
		StringBuilder inventoryPath = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());

		newProduct = this.repository.save(newProduct);
		InventoryDTO inventory = this.getDefaultInventory(newProduct);
		HttpEntity<InventoryDTO> entity = new HttpEntity<InventoryDTO>(inventory);
		restTemplate.exchange(inventoryPath.toString(), HttpMethod.POST, entity,
				InventoryDTO.class);

		return newProduct;
	}

	private InventoryDTO getDefaultInventory(Product product) {
		InventoryDTO inventory = new InventoryDTO();
		inventory.setIdProduct(product.getId());
		inventory.setAverageUnitPrice(product.getUnitPrice());
		inventory.setTotal(BigDecimal.ZERO);
		inventory.setQtdAvailable(0);

		return inventory;
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
		Product verify = this.getById(updatedProduct.getId());
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
