package com.hashkart.productmicroservice.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.productmicroservice.dao.ProductQueryFilter;
import com.hashkart.productmicroservice.dao.ProductRepository;
import com.hashkart.productmicroservice.domain.Product;
import com.hashkart.productmicroservice.enums.SortCriteria;
import com.hashkart.productmicroservice.response.ProductListResponse;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Value("${app.page-size:5}")
	private Integer pageSize;

	@Override
	public Product addNewProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public Product getProduct(Long productId) throws ResourceNotFoundException {
		return productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product Found."));
	}

	@Override
	public ProductListResponse getProducts(String category, String type, Integer rating, boolean inStockOnly, SortCriteria sortCriteria, Integer page) {
		int quantity = inStockOnly ? 1 : 0;
		Specification<Product> specification = getProductSpecification(category, type, rating);
		List<Product> products = productRepo
				.findAll(specification,
						PageRequest.of(page, pageSize, getSortingOrder(sortCriteria)))
				.getContent().stream().filter(product -> product.getQuantity() >= quantity).collect(Collectors.toList());
		return new ProductListResponse(products);
	}

	@Override
	public Product updateProductQuantity(Long productId, Product product) {
		Product productFromDB = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("No Product Found."));
		return productRepo.save(this.updateProductQuantity(product, productFromDB));
	}

	@Override
	public ProductListResponse getBulkProductDetails(Set<Long> productIds) {
		return new ProductListResponse(productRepo.findAllById(productIds));
	}
	
	private Specification<Product> getProductSpecification(String category, String type, Integer rating) {
		Specification<Product> categorySpec = ProductQueryFilter.categoryEquals(category);
		Specification<Product> typeSpec = ProductQueryFilter.typeEquals(type);
		Specification<Product> ratingSpec = ProductQueryFilter.ratingGreaterThanOrEqualTo(rating);
		return Specification.where(categorySpec).and(typeSpec).and(ratingSpec);
	}

	private Product updateProductQuantity(Product product, Product productFromDB) {
		if (product.getQuantity() != null) {
			int newQuantity = productFromDB.getQuantity() - product.getQuantity();
			if(newQuantity < 0) {
				throw new ResourceNotFoundException("Request quantity of the following products is greater than stock quantity: " + productFromDB.getProductId());
			}
			productFromDB.setQuantity(newQuantity);
		}
		return productFromDB;
	}

	private Sort getSortingOrder(SortCriteria sortCriteria) {
		Sort sort = Sort.unsorted();
		if (sortCriteria != null) {
			sort = sortCriteria.equals(SortCriteria.PRICE_ASC) ? Sort.by("price").ascending()
					: Sort.by("price").descending();
		}
		return sort;
	}

	@Override
	public ProductListResponse bulkUpdateProductQuantity(Map<Long, Integer> productQuantities) {
		List<Product> products = productRepo.findAllById(productQuantities.keySet());
		List<Product> outOfStockProducts = products.stream().filter(product -> product.getQuantity() < productQuantities.get(product.getProductId())).collect(Collectors.toList());
		if(!outOfStockProducts.isEmpty()) {
			String outoutOfStockProductIds = outOfStockProducts.stream().map(product -> String.valueOf(product.getProductId())).collect(Collectors.joining(","));
			throw new ResourceNotFoundException("Request quantity of the following products is greater than stock quantity: " + outoutOfStockProductIds);
		}
		products.forEach(product -> product.setQuantity(product.getQuantity() - productQuantities.get(product.getProductId())));
		return new ProductListResponse(productRepo.saveAll(products));
	}
}
