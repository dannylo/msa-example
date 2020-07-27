package com.msaexample.product.servicerequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.dto.TransactionDTO;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;

@Component
public class TransactionServiceRequest {

	private RestTemplate rest;
	
	private ObjectMapper mapper;

	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;

	@Autowired
	private InventoryConfig inventoryConfig;

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


	public BundleDTO processTransactions(List<Request> requests) throws JsonParseException, JsonMappingException, IOException {
		List<TransactionDTO> transactions = this.convert(requests);
		HttpEntity<List<TransactionDTO>> entity = new HttpEntity<List<TransactionDTO>>(transactions);
		
		ResponseEntity<String> response = rest.exchange(this.inventoryConfig.getURLPrefixWithRootPath().toString(), HttpMethod.POST, entity,
				String.class);
		
		if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) { 
			throw new InventoryApiException(response.getBody());
		}
		
		BundleDTO bundle = mapper.readValue(response.getBody(), BundleDTO.class);
		
		return bundle;
		
	}

}
