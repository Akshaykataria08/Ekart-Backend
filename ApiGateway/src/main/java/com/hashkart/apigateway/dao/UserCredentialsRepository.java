package com.hashkart.apigateway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hashkart.apigateway.domain.UserCredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String>{

}
