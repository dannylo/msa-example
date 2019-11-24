package com.msaexample.creditcustommer.reciever;

import java.time.LocalDate;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.creditsystem.CreditResponse;
import com.msaexample.creditcustommer.domain.CreditHistory;
import com.msaexample.creditcustommer.dto.CustomerData;
import com.msaexample.creditcustommer.dto.ResponseDTO;
import com.msaexample.creditcustommer.enums.PaymentSystem;
import com.msaexample.creditcustommer.enums.Status;
import com.msaexample.creditcustommer.sender.ResultCreditSender;
import com.msaexample.creditcustommer.service.CreditHistoryService;

@RabbitListener(queues = {"${queue.order.name}"})
@Component
public class QueueReciever {
	
	@Autowired
	private CreditHistoryService service;
	
	@Autowired
	private ResultCreditSender sender;
	
	@RabbitHandler
	public void listener(@Payload CustomerData customerData) {
		customerData.setPaymentSystem(PaymentSystem.PAGSEGURO);
		CreditResponse result = customerData.performPayment();
	
		CreditHistory history = new CreditHistory();
		history.setOperationId(customerData.getOperationId());
		history.setAuthorizationCode(result.getAuthorizationCode());
		history.setProcessingDate(LocalDate.now());
		history.setStatus(result.getStatus());
		service.save(history);
		
		ResponseDTO response = new ResponseDTO(history.getAuthorizationCode(), 
				history.getStatus(), 
				history.getOperationId());
		
		sender.send(response);
		
	}
	
}
