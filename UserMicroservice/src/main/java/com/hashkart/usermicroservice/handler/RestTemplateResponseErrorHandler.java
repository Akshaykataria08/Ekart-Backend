package com.hashkart.usermicroservice.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.hashkart.commonutilities.exception.RestTemplateException;
import com.hashkart.commonutilities.response.GenericApiResponse;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
		          || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		this.objectMapper.registerModule(new JSR310Module());
		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
			String errorCode = "";
			if(response.getStatusCode().is4xxClientError()) {
				errorCode = "BAD_REQUEST";
			} else {
				errorCode = "INTERNAL_SERVER_ERROR";
			}
			try {
				GenericApiResponse<?> expResponse = this.objectMapper.readValue(response.getBody(), GenericApiResponse.class);
				throw new RestTemplateException(expResponse.getError().getErrorMessage(), response.getRawStatusCode(), errorCode);

			} catch (Exception e) {
				throw new RestTemplateException(e.getMessage(), response.getRawStatusCode(), errorCode);
			}
		}
	}

}
