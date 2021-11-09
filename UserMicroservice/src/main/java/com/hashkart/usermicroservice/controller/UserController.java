package com.hashkart.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.commonutilities.response.GenericApiResponse;
import com.hashkart.usermicroservice.domain.UserProfile;
import com.hashkart.usermicroservice.response.UserListResponse;
import com.hashkart.usermicroservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/profile")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GenericApiResponse<UserProfile> createUserProfile(@RequestBody UserProfile profile) {
		GenericApiResponse<UserProfile> response = new GenericApiResponse<UserProfile>();
		response.setData(userService.createUserProfile(profile));
		return response;
	}
	
	@GetMapping("/profile")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<UserListResponse> getAllUserProfiles() {
		GenericApiResponse<UserListResponse> response = new GenericApiResponse<UserListResponse>();
		response.setData(userService.getAllProfiles());
		return response;
	}
}
