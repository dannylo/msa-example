package com.msaexample.product.amqp.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msaexample.product.domain.CreditCard;
import com.msaexample.product.dto.CreditMessageDTO;

@Component
public class SenderCreditOrder{

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue orderQueue;
	
	public void send(CreditMessageDTO message) {
		rabbitTemplate.convertAndSend(orderQueue.getName(), message);
	}

	
}
