package com.hashkart.cartmicroservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.hashkart.cartmicroservice.handler.RestTemplateResponseErrorHandler;

@Configuration
public class HttpConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
	}
}
