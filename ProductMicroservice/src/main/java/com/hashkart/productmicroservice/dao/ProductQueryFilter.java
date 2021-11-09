package com.hashkart.productmicroservice.dao;

import org.springframework.data.jpa.domain.Specification;

import com.hashkart.productmicroservice.domain.Product;

public class ProductQueryFilter {

	public static Specification<Product> categoryEquals(String category) {
		return (root, query, builder) -> category == null ? builder.conjunction()
				: builder.equal(root.get("category"), category);
	}
	
	public static Specification<Product> typeEquals(String type) {
		return (root, query, builder) -> type == null ? builder.conjunction()
				: builder.equal(root.get("type"), type);
	}
	
	public static Specification<Product> ratingGreaterThanOrEqualTo(Integer rating) {
		return (root, query, builder) -> rating == null ? builder.conjunction()
				: builder.greaterThanOrEqualTo(root.get("rating"), rating);
	}
}
