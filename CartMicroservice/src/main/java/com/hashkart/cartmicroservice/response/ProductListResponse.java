package com.hashkart.cartmicroservice.response;

import java.util.List;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse implements Response {

	@NonNull
	private List<ProductResponse> products;
}
