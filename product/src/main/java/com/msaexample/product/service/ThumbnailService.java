package com.msaexample.product.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.msaexample.product.config.ImageConfig;

@Service
public class ThumbnailService {

	@Autowired
	private ImageConfig imageConfig;

	public void uploadThumbnail(MultipartFile file) throws IOException {
		StringBuilder path = new StringBuilder(imageConfig.getSource());
		path.append(File.separator).append(StringUtils.cleanPath(file.getOriginalFilename()));

		Path source = Paths.get(path.toString());

		Files.copy(file.getInputStream(), source, StandardCopyOption.REPLACE_EXISTING);

	}
	
	public InputStream downloadThumbnail(String originalName) {
		StringBuilder path = new StringBuilder(imageConfig.getSource());
		path.append(File.separator).append(originalName);

		InputStream stream = getClass()
				.getResourceAsStream(path.toString());
		
		return stream;
	}

}
