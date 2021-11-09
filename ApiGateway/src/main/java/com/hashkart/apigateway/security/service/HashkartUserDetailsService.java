package com.hashkart.apigateway.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hashkart.apigateway.dao.UserCredentialsRepository;
import com.hashkart.apigateway.domain.UserCredentials;
import com.hashkart.apigateway.security.domain.HashkartUserDetails;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.UnAuthorizedException;

@Service
public class HashkartUserDetailsService implements UserDetailsService {

	@Autowired
	private UserCredentialsRepository userCredRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final String USER_DOESNT_EXISTS_MSG = "User with email id %s doesn't exists";
	private static final String USER_ALREADY_EXISTS_MSG = "User with email id %s already exists";
	private static final String INVALID_PASSWORD_MSG = "Invalid Password";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCredentials user = userCredRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_DOESNT_EXISTS_MSG, username)));
		return new HashkartUserDetails(user);
	}

	public UserDetails signUpUser(UserCredentials userCreds) {
		if(userCredRepo.existsById(userCreds.getUserName())) {
			throw new ResourceAlreadyExistsException(String.format(USER_ALREADY_EXISTS_MSG, userCreds.getUserName()));
		}
		userCreds.setPassword(bCryptPasswordEncoder.encode(userCreds.getPassword()));
		userCredRepo.save(userCreds);
		return new HashkartUserDetails(userCreds);
	}
	
	public UserDetails authenticateUser(UserCredentials userCreds) throws UnAuthorizedException {
		UserDetails userDetails = this.loadUserByUsername(userCreds.getUserName());
		if(!bCryptPasswordEncoder.matches(userCreds.getPassword(), userDetails.getPassword())) {
			throw new BadCredentialsException(INVALID_PASSWORD_MSG);
		}
		return userDetails;
	}
}
