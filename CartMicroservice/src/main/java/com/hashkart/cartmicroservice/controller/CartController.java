package com.hashkart.cartmicroservice.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	private static final String CART_ID = "userId";

	@PostMapping("/{cartId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GenericApiResponse<Cart> creatCart(@PathVariable String cartId, HttpServletRequest request) {
		GenericApiResponse<Cart> response = new GenericApiResponse<>();
		cartService.createCart(cartId);
		return response;
	}

	@PostMapping("/products/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> addProductItem(HttpServletRequest request, @PathVariable Long productId) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.addProductToCart(request.getHeader(CART_ID), productId));
		return response;
	}
	
	@PutMapping("/products/{productId}/quantity/{quantity}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> updateProductQuantity(HttpServletRequest request, @PathVariable Long productId,
			@PathVariable Integer quantity) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.updateProductQuantity(request.getHeader(CART_ID), productId, quantity));
		return response;
	}

	@DeleteMapping("/products/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> deleteProductItem(HttpServletRequest request, @PathVariable Long productId) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.removeProductFromCart(request.getHeader(CART_ID), productId));
		return response;
	}

	@GetMapping("/products")
	@ResponseStatus(code = HttpStatus.OK)
	public GenericApiResponse<CartItemsResponse> getCartItems(HttpServletRequest request) {
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.getCartItems(request.getHeader(CART_ID)));
		return response;
	}

//	Later lock the products in the cart
	@PostMapping("/checkout")
	public GenericApiResponse<CartItemsResponse> checkoutCart(HttpServletRequest request, @RequestBody(required = false) CheckoutDetailsRequest checkoutDetailsRequest) {
		String couponId = checkoutDetailsRequest != null ? checkoutDetailsRequest.getCouponId(): null;
		GenericApiResponse<CartItemsResponse> response = new GenericApiResponse<CartItemsResponse>();
		response.setData(cartService.checkoutCart(request.getHeader(CART_ID), couponId));
		return response;
	}
}
