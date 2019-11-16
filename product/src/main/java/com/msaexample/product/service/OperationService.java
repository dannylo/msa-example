package com.msaexample.product.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.amqp.sender.SenderCreditOrder;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.enums.OperationType;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.OperationRepository;
import com.msaexample.product.servicerequest.TransactionServiceRequest;

@Service
public class OperationService {

	@Autowired
	private OperationRepository repository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private TransactionServiceRequest serviceRequest;
	
	@Autowired
	private SenderCreditOrder sender;
	
	private Logger logger = LoggerFactory.getLogger(OperationService.class);


	private boolean validateRequests(List<Request> requests) {
		requests.stream().forEach(r -> {
			try {
				r.setProduct(this.productService.getById(r.getProduct().getId()));
				r.setTotal(r.getProduct().getUnitPrice().multiply(
						new BigDecimal(r.getQtd() * -1)));
			} catch (ProductException e) {
				r.setProduct(null);
				logger.error(e.getMessage());
			}
		});

		return !requests.stream().anyMatch(r -> r.getProduct() == null);
	}

	@Transactional
	public Operation save(Operation operation)
			throws ProductException, JsonParseException, JsonMappingException, IOException, CustomerException {
		if (!validateRequests(operation.getRequests())) {
			throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
		}

		operation.setCustomer(this.customerService.getById(operation.getCustomer().getId()));
		this.serviceRequest.processTransactions(operation.getRequests());
		this.sender.send(operation.getCustomer().getCreditCard());
		return repository.save(operation);
	}

}
