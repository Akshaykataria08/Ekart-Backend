package com.hashkart.cartmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hashkart.cartmicroservice.domain.Cart;
import com.hashkart.cartmicroservice.request.CheckoutDetailsRequest;
import com.hashkart.cartmicroservice.response.CartItemsResponse;
import com.hashkart.cartmicroservice.service.CartService;
import com.hashkart.commonutilities.response.GenericApiResponse;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/{cartId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	private GenericApiResponse<Cart> creatCart(@PathVariable String cartId) {
		GenericApiResponse<Cart> response = new GenericApiResponse<>();
		response.setData(cartService.createCart(cartId));
		return response;
	}

	@PostMapping("/{cartId}/products/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> addProductItem(@PathVariable String cartId, @PathVariable Long productId) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.addProductToCart(cartId, productId));
		return response;
	}
	
	@PutMapping("/{cartId}/products/{productId}/quantity/{quantity}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> updateProductQuantity(@PathVariable String cartId, @PathVariable Long productId,
			@PathVariable Integer quantity) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.updateProductQuantity(cartId, productId, quantity));
		return response;
	}

	@DeleteMapping("/{cartId}/products/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> deleteProductItem(@PathVariable String cartId, @PathVariable Long productId) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.removeProductFromCart(cartId, productId));
		return response;
	}

	@GetMapping("/{cartId}/products")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> getCartItems(@PathVariable String cartId) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.getCartItems(cartId));
		return response;
	}

//	Later lock the products in the cart
	@PostMapping("/{cartId}/checkout")
	public GenericApiResponse<CartItemsResponse> checkoutCart(@PathVariable String cartId, @RequestBody(required = false) CheckoutDetailsRequest request) {
		String couponId = request != null ? request.getCouponId(): null;
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.checkoutCart(cartId, couponId));
		return response;
	}
}
