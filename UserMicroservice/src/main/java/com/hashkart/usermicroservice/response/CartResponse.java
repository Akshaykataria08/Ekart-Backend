package com.hashkart.usermicroservice.response;

import java.util.LinkedHashMap;
import java.util.Map;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse implements Response {

	private static final long serialVersionUID = 1L;

	private String cartId;
	
	private Map<Long, Integer> productQuantities = new LinkedHashMap<>();
	private boolean checkout;
}
