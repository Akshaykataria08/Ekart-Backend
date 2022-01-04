package com.hashkart.apigateway.security.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class TokenBasedAuthentication extends AbstractAuthenticationToken {

	private String token;
	private final UserDetails userDetails;
	
	public TokenBasedAuthentication(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;
	}

	@Override
	public Object getCredentials() {
		return this.token;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}
	
	@Override
	public boolean isAuthenticated() {
		return true;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

}
