package com.hashkart.apigateway.request;

import com.hashkart.commonutilities.request.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"password"})
public class LoginRequest implements Request {

	private String username;
	private String password;
}
