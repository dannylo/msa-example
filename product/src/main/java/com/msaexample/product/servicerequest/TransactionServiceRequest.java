package com.msaexample.product.servicerequest;

import java.io.IOException;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.product.amqp.sender.SenderCreditOrder;
import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.domain.Product;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;
import com.msaexample.product.service.ProductService;
/*
 TODO: Refatorar a classe para TransactionServiceRequest (pois não é um serviço própio do MS)
 , e um método que deve salvar o grupo de transações, atrelando ao customer. Deve salvar, se
 o serviço de inventário conseguir processar o pedido das transações, e em seguida solicitar 
 o crédito no Broker. Em caso negativo, um rollback deve ser realizado.
 
 * Usar LOGS no lugar do printStackTrace
 * Associar Customer ao grupo de transações processadas
 * Verificar o uso de DTOs no inventário (MS)
 * 
*/
@Component
public class TransactionServiceRequest {

	private RestTemplate rest;
	
	private ObjectMapper mapper;

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
		
		this.mapper = new ObjectMapper();
	}
	
	private List<TransactionDTO> convert(List<Request> requests){
		List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
		requests.stream().forEach(r -> {
			TransactionDTO transaction = new TransactionDTO(r.getProduct().getId(), r.getQtd());
			transactions.add(transaction);
		});
		
		return transactions;
	}


	public BundleDTO processTransactions(List<Request> requests) throws ProductException, JsonParseException, JsonMappingException, IOException {
		List<TransactionDTO> transactions = this.convert(requests);
		StringBuilder path = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());	

		HttpEntity<List<TransactionDTO>> entity = new HttpEntity<List<TransactionDTO>>(transactions);
		path.append("/transaction");
		
		ResponseEntity<String> response = rest.exchange(path.toString(), HttpMethod.POST, entity,
				String.class);
		
		if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) { 
			throw new InventoryApiException(response.getBody());
		}
		
		BundleDTO bundle = mapper.readValue(response.getBody(), BundleDTO.class);
		
		return bundle;
		
	}

}
