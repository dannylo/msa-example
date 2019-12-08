package com.msaexample.creditcustommer.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msaexample.creditcustommer.dto.ResponseDTO;


@Component
public class ResultCreditSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue responseQueue;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public void send(ResponseDTO response) throws JsonProcessingException {
		String json = mapper.writeValueAsString(response);
		rabbitTemplate.convertAndSend(responseQueue.getName(), json);
	}
	
}
