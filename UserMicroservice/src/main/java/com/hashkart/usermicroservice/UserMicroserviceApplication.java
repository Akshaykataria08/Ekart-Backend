package com.hashkart.usermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hashkart.usermicroservice")
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}
}
