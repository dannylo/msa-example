package com.msaexample.creditcustommer.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.dto.CustomerData;


@Component
public class ResultCreditSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;
	
	public void send(CustomerData customerData) {
		rabbitTemplate.convertAndSend(queue.getName(), customerData);
	}
	
}
