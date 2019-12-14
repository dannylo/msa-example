package com.msaexample.product.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.config.InventoryConfig;
import com.msaexample.product.domain.Product;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.repository.ProductRepository;
import com.msaexample.product.service.ProductService;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

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
	public void savingValidProduct() {
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
		} catch (JsonParseException e) {
			fail(e.getMessage());
		} catch (JsonMappingException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		verify(repository).save(product);
	}

	@Test(expected = ProductException.class)
	public void savingProductWithPriceZero() throws ProductException, JsonParseException, JsonMappingException, IOException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test name");
		product.setUnitPrice(BigDecimal.ZERO);

		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));

		this.service.save(product);
	}
	
	@Test(expected = ProductException.class)
	public void savingProductWithNegativePrice() throws ProductException, JsonParseException, JsonMappingException, IOException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test name");
		product.setUnitPrice(BigDecimal.valueOf(-50.0));

		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));

		this.service.save(product);
	}
	
	@Test(expected = ProductException.class)
	public void savingProductWithNullPrice() throws ProductException, JsonParseException, JsonMappingException, IOException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test name");
		product.setUnitPrice(null);

		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));

		this.service.save(product);
	}



	@Test(expected = ProductException.class)
	public void savingProductWithEmptyName() throws ProductException, JsonParseException, JsonMappingException, IOException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("");
		product.setUnitPrice(BigDecimal.TEN);

		when(repository.save(product)).thenReturn(product);
		when(inventoryConfig.getURLPrefix()).thenReturn(new StringBuilder("http://localhost:8081"));

		this.service.save(product);
	}
	
	@Test
	public void searchProductByValidId() throws ProductException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test Product 2");
		product.setUnitPrice(BigDecimal.TEN);

		Optional<Product> opt = Optional.of(product);
		when(repository.findById(2)).thenReturn(opt);

		Product result = service.getById(2);
		Assert.assertSame(product, result);
	}
	
	@Test(expected = ProductException.class)
	public void searchProductByInvalidId() throws ProductException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test Product 2");
		product.setUnitPrice(BigDecimal.TEN);

		Optional<Product> opt = Optional.of(product);
		when(repository.findById(2)).thenReturn(opt);

		service.getById(0);
	}
	
	@Test(expected = ProductException.class)
	public void searchProductByInexistentId() throws ProductException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test Product 2");
		product.setUnitPrice(BigDecimal.TEN);

		Optional<Product> opt = Optional.of(product);
		
		when(repository.findById(2)).thenReturn(opt);
		when(repository.findById(1)).thenReturn(Optional.empty());

		service.getById(1);
	}
	
	@Test(expected = ProductException.class)
	public void updatingProductNotStored() throws ProductException {
		Product product = new Product();
		product.setDescription("Test Product");
		product.setName("Test Product 2");
		product.setUnitPrice(BigDecimal.TEN);

		Optional<Product> opt = Optional.of(product);
		
		when(repository.findById(2)).thenReturn(opt);
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		product.setId(1);
		
		service.updated(product);
	}
	
	@Test(expected = ProductException.class)
	public void deletingProductNotStored() throws ProductException {
		Product product = new Product();
		product.setId(1);
		
		Optional<Product> opt = Optional.of(product);
		
		when(repository.findById(2)).thenReturn(opt);
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		
		service.remove(product);
	}
}
