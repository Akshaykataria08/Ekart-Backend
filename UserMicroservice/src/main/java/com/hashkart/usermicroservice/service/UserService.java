package com.hashkart.usermicroservice.service;

import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.usermicroservice.domain.UserProfile;
import com.hashkart.usermicroservice.response.UserListResponse;

public interface UserService {

	UserProfile createUserProfile(UserProfile profile) throws ResourceAlreadyExistsException;

	UserListResponse getAllProfiles();

}
