package com.hashkart.usermicroservice.service;

import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.usermicroservice.domain.UserProfile;

public interface UserService {

	UserProfile createUserProfile(UserProfile profile) throws ResourceAlreadyExistsException;

	UserProfile getProfile(String userId);

}
