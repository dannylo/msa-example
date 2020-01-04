package com.msaexample.product.service;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.amqp.sender.SenderCreditOrder;
import com.msaexample.product.domain.Customer;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.CreditMessageDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.enums.OperationType;
import com.msaexample.product.enums.StatusOperation;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.OperationException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.OperationRepository;
import com.msaexample.product.servicerequest.TransactionServiceRequest;
import com.msaexample.product.validation.AbstractValidationMediator;
import com.msaexample.product.validation.operation.OperationValidationMediator;

/**
 * Classe é responsável por lidar com operações que envolvem os produtos e os clientes.
 * 
 * @author Dannylo Johnathan
 * */
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
	
	private AbstractValidationMediator<Operation> validator = new OperationValidationMediator();

	@Autowired
	private SenderCreditOrder sender;

	private Logger logger = LoggerFactory.getLogger(OperationService.class);

	private boolean verifiyAndNormalizeRequests(List<Request> requests) {
		requests.stream().forEach(r -> {
			try {
				r.setProduct(this.productService.getById(r.getProduct().getId()));
				r.calculateTotal();
			} catch (ProductException e) {
				r.setProduct(null);
			}
		});

		return !requests.stream().anyMatch(r -> r.getProduct() == null);
	}
	
	private void sendCreditRequest(Operation operation) {
		CreditMessageDTO message = new CreditMessageDTO(operation.getId(), 
				operation.getCustomer().getCreditCard(),
				operation.getTotal());
		try {
			this.sender.send(message);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}

	@Transactional
	public Operation save(Operation operation)
			throws ProductException, JsonParseException, JsonMappingException, IOException, CustomerException, OperationException {
		
		if(validator.verify(operation).exists()) {
			throw new OperationException(ExceptionMessages.OPERATION_INVALID_PROCESS);
		}

		if (operation.getId() == 0) {
			//first 
			if (!verifiyAndNormalizeRequests(operation.getRequests())) {
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
	
	public Operation getById(Long id) throws OperationException {
		Optional<Operation> opt = this.repository.findById(id);
		if (!opt.isPresent()) {
			throw new OperationException(ExceptionMessages.OPERATION_NOT_FOUND);
		}

		return opt.get();
	}
	
	public List<Operation> getOperationsByCustomer(int idCustomer) throws CustomerException{
		Customer validated = this.customerService.getById(idCustomer);
		return this.repository.findByCustomer(validated);
	}
	
	/* The rollback possibility is only for SALE's Operation. */
	public void rollbackOperation(Operation operation) 
			throws JsonParseException, JsonMappingException, ProductException, IOException, OperationException {
		if(operation.getType() == OperationType.SALE 
				&& operation.getStatus() == StatusOperation.DENIED) {
			List<Request> requests = operation.getRequests()
					.stream().map(r -> {
						r.setQtd(r.getQtd() * -1); 
						return r;
					}).collect(Collectors.toList());
			
			this.serviceRequest.processTransactions(requests);
		} else {
			throw new OperationException(ExceptionMessages.OPERATION_INVALID_PROCESS);
		}
	}

	
}
