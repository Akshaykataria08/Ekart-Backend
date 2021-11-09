package com.hashkart.couponmicroservice.service;

import com.hashkart.couponmicroservice.domain.Coupon;

public interface CouponService {

	Coupon createCoupon(Coupon coupon);

	Coupon getCoupon(String couponId);

	void deleteCoupon(String couponId);

}
