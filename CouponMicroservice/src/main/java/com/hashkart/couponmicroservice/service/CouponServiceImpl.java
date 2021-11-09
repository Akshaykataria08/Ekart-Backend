package com.hashkart.couponmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.couponmicroservice.dao.CouponRepository;
import com.hashkart.couponmicroservice.domain.Coupon;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponRepository couponRepo;
	
	@Override
	public Coupon createCoupon(Coupon coupon) {
		if(couponRepo.existsById(coupon.getCouponId())) {
			throw new ResourceAlreadyExistsException("Coupon with Id " + coupon.getCouponId() + " already exists");
		}
		return couponRepo.save(coupon);
	}

	@Override
	public Coupon getCoupon(String couponId) {
		return getCouponOrThrowException(couponId);
	}

	@Override
	public void deleteCoupon(String couponId) {
		this.getCouponOrThrowException(couponId);
		couponRepo.deleteById(couponId);
	}
	
	private Coupon getCouponOrThrowException(String couponId) {
		return couponRepo.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Coupon with Id " + couponId + " doesn't exists"));
	}

}
