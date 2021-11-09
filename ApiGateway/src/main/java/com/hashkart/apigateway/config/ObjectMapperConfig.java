package com.hashkart.apigateway.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Configuration
public class ObjectMapperConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper =  new ObjectMapper();
		objectMapper.registerModule(new JSR310Module());
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyy'T'HH:mm:ss.SSS"));
		return objectMapper;
	}
}
