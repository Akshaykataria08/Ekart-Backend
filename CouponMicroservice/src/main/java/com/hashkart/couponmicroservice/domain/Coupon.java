package com.hashkart.couponmicroservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon implements Response {

	@Id
	@NonNull
	private String couponId;
	@NonNull
	private Integer couponDiscount;
}
