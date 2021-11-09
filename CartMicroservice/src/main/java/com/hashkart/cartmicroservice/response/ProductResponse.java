package com.hashkart.cartmicroservice.response;

import com.hashkart.commonutilities.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Response {

	private Long productId;
	private String name;
	private String category;
	private String type;
	private Integer quantity;
	private Double price;
	private String brand;
	private Integer rating;
	private Integer requestedQuantity;
	private Integer defaultDiscountApplied;
	private Double discountedPrice;
	
	public ProductResponse(Long productId) {
		super();
		this.productId = productId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductResponse other = (ProductResponse) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
}
