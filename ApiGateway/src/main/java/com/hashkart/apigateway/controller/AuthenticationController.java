package com.hashkart.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.apigateway.request.SignUpRequest;
import com.hashkart.apigateway.response.UserProfileResponse;
import com.hashkart.apigateway.service.AuthenticationService;
import com.hashkart.commonutilities.response.GenericApiResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping
	public String successfulLogin() {
		return "WELCOME";
	}
	
	@PostMapping("/signup")
	public GenericApiResponse<UserProfileResponse> signupUser(@RequestBody SignUpRequest request) {
		GenericApiResponse<UserProfileResponse> response = new GenericApiResponse<>();
		response.setData(authenticationService.signupUser(request));
		return response;
	}
}
