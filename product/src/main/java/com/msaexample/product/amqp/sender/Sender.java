package com.msaexample.product.amqp.sender;

public interface Sender<E> {
	
	void send(E e);
}
