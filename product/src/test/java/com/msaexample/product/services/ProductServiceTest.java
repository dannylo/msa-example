package com.msaexample.product.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Product;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.ProductRepository;
import com.msaexample.product.service.ProductService;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

public class ProductServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private InventoryConfig inventoryConfig;
	
	@InjectMocks
	private ProductService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void salvandoProdutoValido() {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test name");
		product.setUnitPrice(BigDecimal.TEN);
		
		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));
		
		try {
			this.service.save(product);
		} catch (ProductException e) {
			fail(e.getMessage());
		}
		
		verify(repository).save(product);
	}
	
	@Test(expected = ProductException.class)
	public void salvandoProdutoComValorZerado() {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test name");
		product.setUnitPrice(BigDecimal.ZERO);
		
		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));
		
		try {
			this.service.save(product);
		} catch (ProductException e) {
			Assert.
		}
		
		
	}
	
	@Test
	public void salvandoProdutoComNomeVazio() {
		
	}
}