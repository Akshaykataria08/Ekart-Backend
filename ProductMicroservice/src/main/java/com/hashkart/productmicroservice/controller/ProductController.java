package com.hashkart.productmicroservice.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.commonutilities.response.GenericApiResponse;
import com.hashkart.productmicroservice.domain.Product;
import com.hashkart.productmicroservice.enums.SortCriteria;
import com.hashkart.productmicroservice.response.ProductListResponse;
import com.hashkart.productmicroservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GenericApiResponse<Product> addNewProduct(@RequestBody Product product) {
		GenericApiResponse<Product> response = new GenericApiResponse<Product>();
		response.setData(productService.addNewProduct(product));
		return response;
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<ProductListResponse> getProducts(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Integer rating,
			@RequestParam(required = false) boolean inStockOnly,
			@RequestParam(required = false) SortCriteria sort) {
		page = validateAndSetPageNo(page);
		GenericApiResponse<ProductListResponse> response = new GenericApiResponse<ProductListResponse>();
		response.setData(productService.getProducts(category, type, rating, inStockOnly, sort, page));
		return response;
	}
	
	@GetMapping("/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<Product> getProduct(@PathVariable Long productId) {
		GenericApiResponse<Product> response = new GenericApiResponse<Product>();
		response.setData(productService.getProduct(productId));
		return response;
	}
	
	@PutMapping("/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<Product> updateProductQuantity(@PathVariable Long productId, @RequestBody Product product) {
		GenericApiResponse<Product> response = new GenericApiResponse<Product>();
		response.setData(productService.updateProductQuantity(productId, product));
		return response;
	}
	
	@PutMapping("/bulk")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<ProductListResponse> updateProduct(@RequestBody Map<Long, Integer> productQuantities) {
		GenericApiResponse<ProductListResponse> response = new GenericApiResponse<ProductListResponse>();
		response.setData(productService.bulkUpdateProductQuantity(productQuantities));
		return response;
	}
	
	@GetMapping("/bulk")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<ProductListResponse> getBulkProductDetails(@RequestParam Set<Long> productIds) {
		System.out.println(productIds);
		GenericApiResponse<ProductListResponse> response = new GenericApiResponse<ProductListResponse>();
		response.setData(productService.getBulkProductDetails(productIds));
		return response;
	} 
	
	private int validateAndSetPageNo(Integer page) {
		if(page == null || page < 1) {
			page = 1;
		}
		return --page;
	}
}
