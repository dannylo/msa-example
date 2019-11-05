package com.msaexample.product.amqp.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msaexample.product.domain.CreditCard;

@Component
public class SenderCreditOrder implements Sender<CreditCard> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;
	
	@Override
	public void send(CreditCard credit) {
		rabbitTemplate.convertAndSend(queue.getName(), credit);
	}

	
}
