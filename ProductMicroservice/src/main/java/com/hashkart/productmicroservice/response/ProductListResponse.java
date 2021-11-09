package com.hashkart.productmicroservice.response;

import java.util.List;

import com.hashkart.commonutilities.response.Response;
import com.hashkart.productmicroservice.domain.Product;

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
	private List<Product> products;
}
