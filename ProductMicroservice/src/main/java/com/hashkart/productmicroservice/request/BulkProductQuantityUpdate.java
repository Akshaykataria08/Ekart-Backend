package com.hashkart.productmicroservice.request;

import java.util.Map;

import com.hashkart.commonutilities.request.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkProductQuantityUpdate implements Request {

	private Map<Long, Integer> productQuantities;
}
