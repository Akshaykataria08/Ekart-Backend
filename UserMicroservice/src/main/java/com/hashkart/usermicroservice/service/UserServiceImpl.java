package com.hashkart.usermicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.usermicroservice.dao.UserRepository;
import com.hashkart.usermicroservice.domain.UserProfile;
import com.hashkart.usermicroservice.response.UserListResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private HttpConnectorService httpService;

	private static final String CART_MICROSERVICE = "http://localhost:8003";
	private static final String CART_CREATION_PATH = "/cart";
	private static final String USER_ALREADY_EXISTS_MSG = "User with Email id %s already exists";
	private static final String USER_DOESNT_EXISTS_MSG = "User with Email id %s doesn't exists";

	@Override
	public UserProfile createUserProfile(UserProfile profile) throws ResourceAlreadyExistsException {
		if (userRepo.existsById(profile.getUserId())) {
			throw new ResourceAlreadyExistsException(String.format(USER_ALREADY_EXISTS_MSG, profile.getUserId()));
		}
		String url = UriComponentsBuilder
				.fromHttpUrl(CART_MICROSERVICE + CART_CREATION_PATH + "/" + profile.getUserId()).build().toUri()
				.toString();
		httpService.post(url, JsonNode.class);
		return userRepo.save(profile);
	}

	@Override
	public UserProfile getProfile(String userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(USER_DOESNT_EXISTS_MSG, userId)));
	}

}
