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
public class SignUpRequest implements Request {

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String roles;
}
