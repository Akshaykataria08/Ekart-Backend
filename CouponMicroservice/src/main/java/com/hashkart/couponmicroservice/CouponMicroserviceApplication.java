package com.hashkart.couponmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hashkart.couponmicroservice")
public class CouponMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponMicroserviceApplication.class, args);
	}

}
