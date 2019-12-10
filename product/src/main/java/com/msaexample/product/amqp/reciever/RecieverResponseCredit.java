package com.msaexample.product.amqp.reciever;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.product.domain.Operation;
import com.msaexample.product.dto.CreditResponseDTO;
import com.msaexample.product.enums.StatusOperation;
import com.msaexample.product.exception.CustomerException;
import com.msaexample.product.exception.OperationException;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.OperationService;

@RabbitListener(queues = { "${queue.response.name}" })
@Component
public class RecieverResponseCredit {

	@Autowired
	private OperationService operationService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Logger logger = LoggerFactory.getLogger(RecieverResponseCredit.class);

	@RabbitHandler
	public void listener(@Payload String jsonResponse) {
		try {
			
			CreditResponseDTO response = mapper.readValue(jsonResponse, CreditResponseDTO.class);
			Operation operation = operationService.getById(response.getOperationId());
			
			if (response.getStatus() == 0) {
				operation.setStatus(StatusOperation.ACCEPT);
			} else { 
				operation.setStatus(StatusOperation.DENIED); 
				this.operationService.rollbackOperation(operation);
			}
			
			operationService.save(operation);
			
		} catch (OperationException e) {
			logger.error(e.getMessage());
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (ProductException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (CustomerException e) {
			logger.error(e.getMessage());
		}
	}

}
