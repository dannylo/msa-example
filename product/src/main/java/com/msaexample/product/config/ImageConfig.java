package com.msaexample.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "image")
public class ImageConfig {

	private String source;
	
	private String rootVirtualAddress;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRootVirtualAddress() {
		return rootVirtualAddress;
	}

	public void setRootVirtualAddress(String rootVirtualAddress) {
		this.rootVirtualAddress = rootVirtualAddress;
	}
	
	
	
	
}
