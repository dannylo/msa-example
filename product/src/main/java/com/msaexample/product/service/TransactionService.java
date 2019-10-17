package com.msaexample.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;

@Service
public class TransactionService {
	
	private RestTemplate rest;
	
	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;
	
	@Autowired
	private InventoryConfig inventoryConfig;
	
	@Autowired
	private ProductService productService;
	
	@PostConstruct
	public void init() {
		this.rest = new RestTemplate();
		this.rest.setErrorHandler(errorHandler);
	}
	
	private boolean validate(List<TransactionDTO> transactions) {
		transactions.stream().map(t -> {
			try {
				t.setProduct(productService.getById(t.getProduct().getId()));
				t.setTotal(t.getProduct().getUnitPrice().multiply(new BigDecimal(t.getQtd())));
			} catch (ProductException e) {
				t.setProduct(null);
				e.printStackTrace();
			}
			return t;
		});

		List<TransactionDTO> dontValidated = transactions.stream()
				.filter(t -> t.getProduct() == null)
				.collect(Collectors.toList());
		
		return dontValidated.isEmpty();
	}
	

	public void createSale(List<TransactionDTO> transactions) throws ProductException {
		//Nao deve usar o prefixo... e sim /transactions
		StringBuilder path = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());
		if (!validate(transactions)) {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}
		transactions.stream().forEach(t -> {
			if (t.getProduct() != null) {
				HttpEntity<TransactionDTO> entity = new HttpEntity<TransactionDTO>(t);
				path.append("/")
					.append(t.getProduct().getId())
					.append("/output");
				rest.exchange(path.toString(), HttpMethod.POST, entity, TransactionDTO.class);
			}
		});
	}
	
	public void createBuy(List<TransactionDTO> transactions) throws ProductException {
		StringBuilder path = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());
		
		if (!validate(transactions)) {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}
		transactions.stream().forEach(t -> {
			HttpEntity<TransactionDTO> entity = new HttpEntity<TransactionDTO>(t);
			path.append("/")
			.append(t.getProduct().getId())
			.append("/input");
			rest.exchange(path.toString(), HttpMethod.POST, entity, TransactionDTO.class);
		});
	}

}
