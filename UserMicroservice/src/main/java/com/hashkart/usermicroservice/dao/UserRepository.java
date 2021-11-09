package com.hashkart.usermicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hashkart.usermicroservice.domain.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, String>{

}
