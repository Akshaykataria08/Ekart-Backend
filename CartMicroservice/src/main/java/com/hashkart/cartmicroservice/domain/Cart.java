package com.hashkart.cartmicroservice.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart implements Response {
	
	@Id
	private String cartId;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Long, Integer> productQuantities = new LinkedHashMap<>();
	private boolean checkout;
	private Double cartTotal;
	private Integer couponDiscount;
	private Double discountedCartTotal;
	
}
