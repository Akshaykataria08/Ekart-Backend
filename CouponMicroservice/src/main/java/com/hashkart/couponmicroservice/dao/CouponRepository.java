package com.hashkart.couponmicroservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashkart.couponmicroservice.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, String> {

}
