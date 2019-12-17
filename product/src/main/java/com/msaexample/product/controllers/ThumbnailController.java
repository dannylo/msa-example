package com.msaexample.product.controllers;


import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msaexample.product.service.ThumbnailService;

@RestController
public class ThumbnailController {
	
	@Autowired
	private ThumbnailService service;
	
	private Logger logger = LoggerFactory.getLogger(ThumbnailController.class);

	
	@GetMapping(path="/files/{originalName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody byte[] getThumbnail(@PathVariable String originalName) throws IOException {
		logger.info(originalName);
		return IOUtils.toByteArray(this.service
					.downloadThumbnail(originalName));
	}
	
	@PostMapping(name = "/files/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestParam("thumb") MultipartFile thumb){
		try {
			this.service.uploadThumbnail(thumb);
			String virtualAccess = "http://localhost:8080/files/"
					.concat(thumb.getOriginalFilename());
			return new ResponseEntity<String>(virtualAccess, HttpStatus.OK);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
