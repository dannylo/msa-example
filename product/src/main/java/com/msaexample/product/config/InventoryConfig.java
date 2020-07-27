package com.msaexample.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "inventory")
public class InventoryConfig {

	private String url;
	private String port;
	private String root;
	
	
	public StringBuilder getURLPrefix() {
		StringBuilder path = new StringBuilder(this.url);
		path.append(":")
			.append(this.port);
		return path;
	}
	
	public StringBuilder getURLPrefixWithRootPath() {
		return this.getURLPrefix().append(this.getRoot());
	}
	


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
