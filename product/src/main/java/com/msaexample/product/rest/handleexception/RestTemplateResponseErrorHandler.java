package com.msaexample.product.rest.handleexception;

import java.io.IOException;

import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.msaexample.product.enums.ExceptionMessages;
import com.msaexample.product.exception.InventoryApiException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		
		return (response.getStatusCode().series() == Series.CLIENT_ERROR 
		          || response.getStatusCode().series() == Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode()
		          .series() == Series.SERVER_ERROR) {
		            throw new InventoryApiException(ExceptionMessages.INVENTORY_SERVER_ERROR);
		        } else if (response.getStatusCode()
		          .series() == Series.CLIENT_ERROR) {
		            throw new InventoryApiException(response.getStatusText());
		        }
		
	}

}
