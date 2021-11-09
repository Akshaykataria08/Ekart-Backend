package com.hashkart.commonutilities.exception;

import lombok.Getter;

@Getter
public class RestTemplateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int statusCode;
	private String errorCode;

	public RestTemplateException(String message, int statusCode, String errorCode) {
		super(message);
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}
}
