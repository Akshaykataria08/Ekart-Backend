package com.hashkart.usermicroservice.config;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper =  new ObjectMapper();
		return objectMapper;
	}
}
