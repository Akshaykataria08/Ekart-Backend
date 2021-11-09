package com.hashkart.cartmicroservice.response;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse implements Response {

	private String couponId;
	private Integer couponDiscount;
}
