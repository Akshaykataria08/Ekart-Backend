package com.hashkart.usermicroservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Response {

	@Id
	private String userId;
	private String mobileNo;
	private String address;
	private String firstName;
	private String lastName;
}
