package com.msaexample.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msaexample.product.amqp.sender.SenderCreditOrder;
import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.dto.BundleTransactionDTO;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.InventoryApiException;
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

	@Autowired
	private SenderCreditOrder sender;

	@PostConstruct
	public void init() {
		this.rest = new RestTemplate();
		this.rest.setErrorHandler(errorHandler);
	}

	private boolean validateProducts(List<TransactionDTO> transactions) {
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
		boolean dontValidated = transactions.stream().anyMatch(t -> t.getProduct() == null);

		return dontValidated;
	}

	public BundleTransactionDTO sendTransactions(List<TransactionDTO> transactions) throws ProductException, InventoryApiException {
		StringBuilder path = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());
		if (!validateProducts(transactions)) {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}

		HttpEntity<List<TransactionDTO>> entity = new HttpEntity<List<TransactionDTO>>(transactions);
		path.append("/transaction");
		
		ResponseEntity<BundleTransactionDTO> response = rest.exchange(path.toString(), HttpMethod.POST, entity,
				BundleTransactionDTO.class);
		
		if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) { 
			throw new InventoryApiException(ExceptionMessages.INVENTORY_SERVER_ERROR);
		}
		
		return response.getBody();
		
	}

}
