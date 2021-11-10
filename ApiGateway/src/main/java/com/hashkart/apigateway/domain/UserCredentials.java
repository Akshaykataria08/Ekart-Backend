package com.hashkart.apigateway.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.hashkart.apigateway.request.LoginRequest;
import com.hashkart.apigateway.request.SignUpRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"password"})
public class UserCredentials {

	@Id
	private String userName;
	private String password;
	private String roles;
	
	public UserCredentials(LoginRequest request) {
		this.userName = request.getUserName();
		this.password = request.getPassword();
	}
	
	public UserCredentials(SignUpRequest request) {
		this.userName = request.getUserName();
		this.password = request.getPassword();
		this.roles = request.getRoles();
	}
}
