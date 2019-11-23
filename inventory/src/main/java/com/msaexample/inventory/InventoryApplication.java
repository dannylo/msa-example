package com.msaexample.inventory;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories("com.msaexample.inventory")
@EntityScan("com.msaexample.inventory")
@ComponentScan("com.msaexample.inventory")
@SpringBootApplication
public class InventoryApplication {


	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
	


}
