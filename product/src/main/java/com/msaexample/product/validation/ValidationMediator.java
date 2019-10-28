package com.msaexample.product.validation;

public interface ValidationMediator<E> {

	BusinessInvalidation verify(E e);
}
