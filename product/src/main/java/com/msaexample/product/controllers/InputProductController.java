package com.msaexample.product.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msaexample.product.domain.Request;
import com.msaexample.product.dto.BundleDTO;
import com.msaexample.product.exception.ProductException;
import com.msaexample.product.service.InputProductService;

@RestController
@RequestMapping("/input")
public class InputProductController {
	
	@Autowired
	private InputProductService inputService;

	private Logger logger = LoggerFactory.getLogger(InputProductController.class);
	
	@PostMapping
	public ResponseEntity<?> registerInput(@RequestBody List<Request> requests){
		try {
			return new ResponseEntity<BundleDTO>(this.inputService.registerInput(requests), HttpStatus.OK);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (ProductException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
