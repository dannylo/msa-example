package com.msaexample.product.servicerequest;

import java.io.IOException;
import java.math.BigDecimal;

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
import com.msaexample.product.domain.Product;
import com.msaexample.product.dto.InventoryDTO;
import com.msaexample.product.exception.InventoryApiException;
import com.msaexample.product.rest.handleexception.RestTemplateResponseErrorHandler;

@Component
public class InventoryServiceRequest {
	
	private RestTemplate restTemplate;
	
	private ObjectMapper mapper;
	
	@Autowired
	private InventoryConfig inventoryConfig;
	
	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(errorHandler);
		this.mapper = new ObjectMapper();
	}
	
	public InventoryDTO createInventory(Product product) throws JsonParseException, JsonMappingException, IOException {
		InventoryDTO inventory = this.getDefaultInventory(product);
		StringBuilder path = this.inventoryConfig.getURLPrefix().append(this.inventoryConfig.getRoot());	

		HttpEntity<InventoryDTO> entity = new HttpEntity<InventoryDTO>(inventory);
		ResponseEntity<String> response = restTemplate.exchange(path.toString(), HttpMethod.POST ,entity, String.class);
	
		if(response.getStatusCode() != HttpStatus.OK) {
			throw new InventoryApiException(response.getBody());
		}
		
		InventoryDTO created = mapper.readValue(response.getBody(), InventoryDTO.class);
		return created;
	}
	
	private InventoryDTO getDefaultInventory(Product product) {
		InventoryDTO inventory = new InventoryDTO();
		inventory.setIdProduct(product.getId());
		inventory.setAverageUnitPrice(product.getUnitPrice());
		inventory.setTotal(BigDecimal.ZERO);
		inventory.setQtdAvailable(0);

		return inventory;
	}

}
