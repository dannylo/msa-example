package com.msaexample.creditcustommer.creditsystem;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.msaexample.creditcustommer.enums.Status;

/**
 * @author Dannylo Johnathan
 * Classe representa uma resposta do sistema de pagamentos do PagSeguro. 
 * O construtor deve tratar a responsta bruta (JSON) atentando para o retorno do codigo de autorização.
 * 
 * */
public class PagSeguroResponse extends CreditResponse {
	
	private Logger logger = LoggerFactory.getLogger(PagSeguroResponse.class);


	public PagSeguroResponse(String rawJson) {
		super(rawJson);
	}

	@Override
	public void treatResponse(String rawJson) {
		System.out.println(rawJson);
		try {
			JsonNode json = getMapper().readTree(rawJson);
			if(json.get("status").asText().equals("OK")) {
				this.setStatus(Status.PROCESSED);
				this.setAuthorizationCode(json.get("auth").asLong());
			} else {
				this.setStatus(Status.REJECTED);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	
}
