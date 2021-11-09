package com.hashkart.commonutilities.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Response {

	@NonNull
	private String errorCode;
	@NonNull
	private String errorMessage;
	@NonNull
	private Integer statusCode;
	@NonNull
	private LocalDateTime timestamp;
	
}
