package com.msaexample.creditcustommer.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msaexample.creditcustommer.domain.CreditHistory;
import com.msaexample.creditcustommer.dto.CustomerData;
import com.msaexample.creditcustommer.dto.ResponseDTO;


@Component
public class ResultCreditSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue responseQueue;
	
	public void send(ResponseDTO response) {
		rabbitTemplate.convertAndSend(responseQueue.getName(), response);
	}
	
}
