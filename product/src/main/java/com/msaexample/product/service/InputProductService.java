package com.msaexample.product.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.servicerequest.TransactionServiceRequest;

@Service
public class InputProductService {

	@Autowired
	private TransactionServiceRequest serviceRequest;

	@Autowired
	private ProductService productService;

	private boolean verifiyAndNormalizeRequests(List<Request> requests) {
		requests.stream().forEach(r -> {
			try {
				r.setProduct(this.productService.getById(r.getProduct().getId()));
				r.setTotal(r.getProduct().getUnitPrice().multiply(new BigDecimal(r.getQtd())));
			} catch (ProductException e) {
				r.setProduct(null);
			}
		});

		return !(requests.stream().anyMatch(r -> r.getProduct() == null || r.getQtd() < 0));
	}

	public BundleDTO registerInput(List<Request> requests)
			throws ProductException, JsonParseException, JsonMappingException, IOException {
		
		if (!verifiyAndNormalizeRequests(requests)) {
			throw new ProductException(ExceptionMessages.PRODUCTS_OPERATION_INVALID);
		}

		BundleDTO bundle = this.serviceRequest.processTransactions(requests);
		
		return bundle;

	}

}
