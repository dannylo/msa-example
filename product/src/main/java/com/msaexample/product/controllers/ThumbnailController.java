package com.msaexample.product.controllers;


import java.io.IOException;
import java.io.InputStream;

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

import com.msaexample.product.config.ImageConfig;
import com.msaexample.product.service.ThumbnailService;

@RestController
public class ThumbnailController {
	
	@Autowired
	private ThumbnailService service;
	
	@Autowired
	private ImageConfig config;
	
	private Logger logger = LoggerFactory.getLogger(ThumbnailController.class);

	
	@GetMapping("/files/{originalName}")
	public ResponseEntity<?> getThumbnail(@PathVariable String originalName) throws IOException {
		byte[] thumbnail = this.service
				.downloadThumbnail(originalName);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(thumbnail);
	}
	
	@PostMapping(path = "/files/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestParam("thumb") MultipartFile thumb){
		try {
			this.service.uploadThumbnail(thumb);
			String virtualAccess = this.config.getRootVirtualAddress()
					.concat(thumb.getOriginalFilename());
			return new ResponseEntity<String>(virtualAccess, HttpStatus.OK);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
