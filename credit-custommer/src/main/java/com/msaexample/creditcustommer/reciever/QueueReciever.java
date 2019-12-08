package com.msaexample.creditcustommer.reciever;

import java.io.IOException;
import java.time.LocalDate;

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
import com.msaexample.creditcustommer.creditsystem.CreditResponse;
import com.msaexample.creditcustommer.domain.CreditHistory;
import com.msaexample.creditcustommer.dto.CustomerData;
import com.msaexample.creditcustommer.dto.ResponseDTO;
import com.msaexample.creditcustommer.enums.PaymentSystem;
import com.msaexample.creditcustommer.sender.ResultCreditSender;
import com.msaexample.creditcustommer.service.CreditHistoryService;

@RabbitListener(queues = {"${queue.order.name}"})
@Component
public class QueueReciever {
	
	@Autowired
	private CreditHistoryService service;
	
	@Autowired
	private ResultCreditSender sender;
	
	private Logger logger = LoggerFactory.getLogger(QueueReciever.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RabbitHandler
	public void listener(@Payload String jsonCustomer) throws JsonParseException, JsonMappingException, IOException {
		CustomerData customerData = mapper.readValue(jsonCustomer, CustomerData.class);
		customerData.setPaymentSystem(PaymentSystem.PAGSEGURO);
		CreditResponse result = customerData.performPayment();
		
		CreditHistory history = new CreditHistory();
		history.setOperationId(customerData.getOperationId());
		history.setAuthorizationCode(result.getAuthorizationCode());
		history.setProcessingDate(LocalDate.now());
		history.setStatus(result.getStatus());
		history.setValue(customerData.getValueRequested());
		service.save(history);
		
		ResponseDTO response = new ResponseDTO(history.getAuthorizationCode(), 
				history.getStatus(), 
				history.getOperationId());
		
		sender.send(response);
		
	}
	
}
