package com.hashkart.cartmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hashkart.cartmicroservice.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String>{

	
}
