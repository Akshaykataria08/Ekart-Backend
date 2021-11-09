package com.hashkart.productmicroservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hashkart.commonutilities.exception.BadCredentialsException;
import com.hashkart.commonutilities.exception.BadRequestException;
import com.hashkart.commonutilities.exception.InternalServerErrorException;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.commonutilities.exception.RestTemplateException;
import com.hashkart.commonutilities.exception.UnAuthorizedException;
import com.hashkart.commonutilities.response.ErrorResponse;
import com.hashkart.commonutilities.response.GenericApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final String NOT_FOUND = "NOT_FOUND";
	private static final String CONFLICT = "CONFLICT";
	private static final String BAD_REQUEST = "BAD_REQUEST";
	private static final String UNAUTHORIZED = "UNAUTHORIZED";
	private static final String BAD_CREDENTIALS = "BAD_CREDENTIALS";
	private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public GenericApiResponse<?> resourceAlreadyExists(ResourceAlreadyExistsException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(CONFLICT, ex.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResourceNotFoundException.class)
	public GenericApiResponse<?> resourceNotFound(ResourceNotFoundException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(NOT_FOUND, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public GenericApiResponse<?> badRequest(BadRequestException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public GenericApiResponse<?> badCredentials(BadCredentialsException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(BAD_CREDENTIALS, ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(UnAuthorizedException.class)
	public GenericApiResponse<?> unAuthorized(UnAuthorizedException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(UNAUTHORIZED, ex.getMessage(), HttpStatus.FORBIDDEN.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerErrorException.class)
	public GenericApiResponse<?> internalServerError(InternalServerErrorException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()));
		return apiResponse;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RestTemplateException.class)
	public GenericApiResponse<?> restTemplateResponse(RestTemplateException ex) {
		GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
		apiResponse.setError(new ErrorResponse(ex.getErrorCode(), ex.getMessage(), ex.getStatusCode(), LocalDateTime.now()));
		return apiResponse;
	}
}
