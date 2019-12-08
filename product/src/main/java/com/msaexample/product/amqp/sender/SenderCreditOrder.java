package com.msaexample.product.amqp.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.product.dto.CreditMessageDTO;

@Component
public class SenderCreditOrder{

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue orderQueue;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public void send(CreditMessageDTO message) throws JsonProcessingException {
		String json = mapper.writeValueAsString(message);
		rabbitTemplate.convertAndSend(orderQueue.getName(), json);
	}

	
}
