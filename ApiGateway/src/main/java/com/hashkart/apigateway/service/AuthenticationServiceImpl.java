package com.hashkart.apigateway.service;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashkart.apigateway.domain.UserCredentials;
import com.hashkart.apigateway.request.LoginRequest;
import com.hashkart.apigateway.request.SignUpRequest;
import com.hashkart.apigateway.request.UserProfileRequest;
import com.hashkart.apigateway.response.LoginResponse;
import com.hashkart.apigateway.response.UserProfileResponse;
import com.hashkart.apigateway.security.service.HashkartUserDetailsService;
import com.hashkart.apigateway.security.utility.TokenHelper;
import com.hashkart.commonutilities.exception.InternalServerErrorException;
import com.hashkart.commonutilities.response.GenericApiResponse;
import com.hashkart.commonutilities.utility.URLUtility;

@Service
@RefreshScope
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private HashkartUserDetailsService userDetailsService;
	
	@Autowired
	private HttpConnectorService httpService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Value("${userServiceUrl}")
	private String USER_MICROSERVICE;
	@Value("${userServicePath}")
	private String CREATE_USER_PATH;

	@Override
	public UserProfileResponse signupUser(SignUpRequest request) {
		UserCredentials userCreds = new UserCredentials(request);
		UserProfileResponse user = createUser(request);
		UserDetails userDetails = userDetailsService.signUpUser(userCreds);
		return new UserProfileResponse(userDetails.getUsername(), user.getMobileNo(), user.getAddress(), user.getFirstName(), user.getLastName());
	}

	private UserProfileResponse createUser(SignUpRequest request) {
		String url;
		try {
			url = UriComponentsBuilder.fromHttpUrl(URLUtility.createUrl(USER_MICROSERVICE, CREATE_USER_PATH)).build().toUri()
					.toString();
			System.out.println("URL: " + url);
		} catch (URISyntaxException e) {
			throw new InternalServerErrorException("Malformed URL: " + USER_MICROSERVICE);
		}
		UserProfileRequest userRequest = new UserProfileRequest(request);
		JsonNode jsonResponse = httpService.post(url, userRequest, JsonNode.class);
		GenericApiResponse<UserProfileResponse> response = objectMapper.convertValue(jsonResponse,
				new TypeReference<GenericApiResponse<UserProfileResponse>>() {
				});
		return response.getData();
	}

	@Override
	public LoginResponse loginUser(LoginRequest request) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new com.hashkart.commonutilities.exception.BadCredentialsException("Username/Password mismatch");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = tokenHelper.generateToken(userDetails);
		return new LoginResponse(jwt);
	}

}
