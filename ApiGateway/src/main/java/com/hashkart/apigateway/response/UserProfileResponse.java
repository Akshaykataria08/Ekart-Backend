package com.hashkart.apigateway.response;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse implements Response {

	private String userId;
	private String mobileNo;
	private String address;
	private String firstName;
	private String lastName;
	
}
