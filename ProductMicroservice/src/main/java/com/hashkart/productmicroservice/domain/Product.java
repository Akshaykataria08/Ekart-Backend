package com.hashkart.productmicroservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.hashkart.commonutilities.response.Response;
import com.hashkart.commonutilities.utility.UUIDGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Response {

	@Id
	private Long productId = UUIDGenerator.generateUUID();
	private String name;
	private String category;
	private String type;
	private Integer quantity;
	private Double price;
	private String brand;
	private Integer rating;
}
