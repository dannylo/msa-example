package com.msaexample.creditcustommer.reciever;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.service.CreditHistoryService;

@RabbitListener(queues = {"${queue.order.name}"})
@Component
public class QueueReciever {
	
	@Autowired
	private CreditHistoryService service;
	
	@RabbitHandler
	public void listener(@Payload String order) {
		//TODO: tratar order.
		//TODO: Processar pagamento.
		//TODO: Registrar resultado do processamento.
	}
	
}
