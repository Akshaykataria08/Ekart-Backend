package com.hashkart.productmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hashkart.productmicroservice", "com.hashkart.commonutilities.domain"})
public class ProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

}
