package com.hashkart.cartmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hashkart.cartmicroservice")
public class CartMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMicroserviceApplication.class, args);
	}

}
