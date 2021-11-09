package com.hashkart.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashkart.apigateway.domain.UserCredentials;
import com.hashkart.apigateway.request.SignUpRequest;
import com.hashkart.apigateway.request.UserProfileRequest;
import com.hashkart.apigateway.response.UserProfileResponse;
import com.hashkart.apigateway.security.service.HashkartUserDetailsService;
import com.hashkart.commonutilities.response.GenericApiResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private HashkartUserDetailsService userDetailsService;
	
	@Autowired
	private HttpConnectorService httpService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String USER_MICROSERVICE = "http://localhost:8001";
	private static final String CREATE_USER_PATH = "/users/profile";

	@Override
	public UserProfileResponse signupUser(SignUpRequest request) {
		UserCredentials userCreds = new UserCredentials(request);
		UserDetails userDetails = userDetailsService.signUpUser(userCreds);
		UserProfileResponse user = createUser(request);
		return new UserProfileResponse(userDetails.getUsername(), user.getMobileNo(), user.getAddress(), user.getFirstName(), user.getLastName());
	}

	private UserProfileResponse createUser(SignUpRequest request) {
		String url = UriComponentsBuilder.fromHttpUrl(USER_MICROSERVICE + CREATE_USER_PATH).build().toUri()
				.toString();
		UserProfileRequest userRequest = new UserProfileRequest(request);
		JsonNode jsonResponse = httpService.post(url, userRequest, JsonNode.class);
		GenericApiResponse<UserProfileResponse> response = objectMapper.convertValue(jsonResponse,
				new TypeReference<GenericApiResponse<UserProfileResponse>>() {
				});
		return response.getData();
	}

}
