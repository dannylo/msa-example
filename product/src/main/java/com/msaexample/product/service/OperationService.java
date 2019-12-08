package com.msaexample.product.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.amqp.sender.SenderCreditOrder;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.dto.CreditMessageDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.enums.OperationType;
import com.msaexample.product.enums.StatusOperation;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.OperationException;
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
				r.setTotal(r.getProduct().getUnitPrice().multiply(new BigDecimal(r.getQtd() * -1)));
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

		if (operation.getId() == 0) {
			if (!validateRequests(operation.getRequests())) {
				throw new ProductException(ExceptionMessages.PRODUCTS_INVALID);
			}
			operation.calculateTotal();
			operation.setCustomer(this.customerService.getById(operation.getCustomer().getId()));
			this.serviceRequest.processTransactions(operation.getRequests());
			operation = repository.save(operation);
			this.sendCreditRequest(operation); // asynchronous call.
		}
		
		operation = repository.save(operation);
		return operation;
	}

	private void sendCreditRequest(Operation operation) {
		CreditMessageDTO message = new CreditMessageDTO(operation.getId(), operation.getCustomer().getCreditCard(),
				operation.getTotal());
		try {
			this.sender.send(message);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}

	public Operation getById(Long id) throws OperationException {
		Optional<Operation> opt = this.repository.findById(id);
		if (!opt.isPresent()) {
			throw new OperationException(ExceptionMessages.OPERATION_NOT_FOUND);
		}

		return opt.get();
	}
}
