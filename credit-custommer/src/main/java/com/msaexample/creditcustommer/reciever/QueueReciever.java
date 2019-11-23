package com.msaexample.creditcustommer.reciever;

import java.time.LocalDate;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.domain.CreditHistory;
import com.msaexample.creditcustommer.dto.CustomerData;
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
		String result = customerData.performPayment();
		customerData.setResult(result);
		
		CreditHistory history = new CreditHistory();
		history.setOperationId(customerData.getOperationId());
		history.setProcessingDate(LocalDate.now());
		history.setStatus(Status.PROCESSED);
		service.save(history);
		
		sender.send(customerData);
		
	}
	
}
