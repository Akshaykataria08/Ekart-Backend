package com.hashkart.commonutilities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericApiResponse<T extends Response> implements Response {

	private ErrorResponse error;
	private T data;
}
