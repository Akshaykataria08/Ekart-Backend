package com.hashkart.cartmicroservice.request;

import com.hashkart.commonutilities.request.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDetailsRequest implements Request {
	private String couponId;
}
