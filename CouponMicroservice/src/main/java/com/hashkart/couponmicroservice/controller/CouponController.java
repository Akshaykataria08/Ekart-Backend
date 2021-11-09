package com.hashkart.couponmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.commonutilities.response.GenericApiResponse;
import com.hashkart.couponmicroservice.domain.Coupon;
import com.hashkart.couponmicroservice.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@PostMapping
	public GenericApiResponse<Coupon> createCoupon(@RequestBody Coupon coupon) {
		GenericApiResponse<Coupon> response = new GenericApiResponse<>();
		response.setData(couponService.createCoupon(coupon));
		return response;
	}
	
	@GetMapping("/{couponId}")
	public GenericApiResponse<Coupon> getCoupon(@PathVariable String couponId) {
		GenericApiResponse<Coupon> response = new GenericApiResponse<>();
		response.setData(couponService.getCoupon(couponId));
		return response;
	}
	
	@DeleteMapping("/{couponId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCoupon(@PathVariable String couponId) {
		couponService.deleteCoupon(couponId);
	}
}
