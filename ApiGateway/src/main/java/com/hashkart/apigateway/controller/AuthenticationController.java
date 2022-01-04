package com.hashkart.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.apigateway.request.LoginRequest;
import com.hashkart.apigateway.request.SignUpRequest;
import com.hashkart.apigateway.response.LoginResponse;
import com.hashkart.apigateway.response.UserProfileResponse;
import com.hashkart.apigateway.service.AuthenticationService;
import com.hashkart.commonutilities.response.GenericApiResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/auth/signup")
	public GenericApiResponse<UserProfileResponse> signupUser(@RequestBody SignUpRequest request) {
		GenericApiResponse<UserProfileResponse> response = new GenericApiResponse<>();
		response.setData(authenticationService.signupUser(request));
		return response;
	}
	
	@PostMapping("/auth/login")
	public GenericApiResponse<LoginResponse> doLogin(@RequestBody LoginRequest request) {
		GenericApiResponse<LoginResponse> response = new GenericApiResponse<>();
		response.setData(authenticationService.loginUser(request));
		return response;
	}
}
