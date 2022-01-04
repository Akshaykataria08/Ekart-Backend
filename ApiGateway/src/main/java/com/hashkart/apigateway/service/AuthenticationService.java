package com.hashkart.apigateway.service;

import com.hashkart.apigateway.request.LoginRequest;
import com.hashkart.apigateway.request.SignUpRequest;
import com.hashkart.apigateway.response.LoginResponse;
import com.hashkart.apigateway.response.UserProfileResponse;

public interface AuthenticationService {

	UserProfileResponse signupUser(SignUpRequest request);

	LoginResponse loginUser(LoginRequest request);

}
