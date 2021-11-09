package com.hashkart.productmicroservice.service;

import java.util.Map;
import java.util.Set;

import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.productmicroservice.domain.Product;
import com.hashkart.productmicroservice.enums.SortCriteria;
import com.hashkart.productmicroservice.response.ProductListResponse;

public interface ProductService {

	Product addNewProduct(Product product);

	ProductListResponse getProducts(String category, String type, Integer rating, boolean inStockOnly, SortCriteria sortCriteria, Integer page);

	Product getProduct(Long productId) throws ResourceNotFoundException;

	Product updateProductQuantity(Long productId, Product product);

	ProductListResponse getBulkProductDetails(Set<Long> productIds);

	ProductListResponse bulkUpdateProductQuantity(Map<Long, Integer> productQuantities);

}
