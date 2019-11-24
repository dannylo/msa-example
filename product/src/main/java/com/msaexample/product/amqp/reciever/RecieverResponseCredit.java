package com.msaexample.product.amqp.reciever;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.msaexample.product.dto.CreditResponseDTO;
import com.msaexample.product.service.OperationService;


@RabbitListener(queues = {"${queue.response.name}"})
@Component
public class RecieverResponseCredit {
	
	@Autowired
	private OperationService operationService;

	@RabbitHandler
	public void listener(@Payload CreditResponseDTO response) {
		//TODO: TRATAR RESPOSTA DE CRÉDITO, SE FOR POSITIVA, ATUALIZAR STATUS DA OPERATION.
		if(response.getStatus() == 1) {
			
		}
	}

}
