package com.hashkart.apigateway.request;

import com.hashkart.commonutilities.request.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest implements Request {

	private String userId;
	private String mobileNo;
	private String address;
	private String firstName;
	private String lastName;
	
	public UserProfileRequest(SignUpRequest request) {
		this.userId = request.getUserName();
		this.firstName = request.getFirstName();
		this.lastName = request.getLastName();
	}
}
