package com.hashkart.apigateway.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.hashkart.apigateway.handler.RestTemplateResponseErrorHandler;

@Configuration
public class HttpConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
	}
}
