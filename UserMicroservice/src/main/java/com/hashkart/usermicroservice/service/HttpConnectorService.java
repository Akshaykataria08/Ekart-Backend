package com.hashkart.usermicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpConnectorService {
	
	@Autowired
	private RestTemplate restTemplate;

	public <R> R get(String url, Class<R> className) {
		ResponseEntity<R> response;
		response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), className);
		return response.getBody();
	}
	
	public <T, R> R patch(String url, T payload, Class<R> className) {
		ResponseEntity<R> response;
		HttpEntity<T> httpEntity = new HttpEntity<T>(payload);
		response = restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, className);
		return response.getBody();
	}
	
	public <T, R> R post(String url, T payload, Class<R> className) {
		ResponseEntity<R> response;
		HttpEntity<T> httpEntity = new HttpEntity<T>(payload);
		response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, className);
		return response.getBody();
	}
	
	public <R> R post(String url, Class<R> className) {
		ResponseEntity<R> response;
		response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null), className);
		return response.getBody();
	}
}
