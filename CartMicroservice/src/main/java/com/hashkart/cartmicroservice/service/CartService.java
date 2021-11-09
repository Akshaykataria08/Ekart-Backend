package com.hashkart.cartmicroservice.service;

import com.hashkart.cartmicroservice.domain.Cart;
import com.hashkart.cartmicroservice.response.CartItemsResponse;
import com.hashkart.commonutilities.exception.BadRequestException;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;

public interface CartService {

	Cart createCart(String cartId) throws ResourceAlreadyExistsException;

	CartItemsResponse addProductToCart(String cartId, Long productId) throws ResourceNotFoundException;

	CartItemsResponse updateProductQuantity(String cartId, Long productId, Integer quantity) throws BadRequestException, ResourceNotFoundException;

	CartItemsResponse removeProductFromCart(String cartId, Long productId) throws ResourceNotFoundException;

	CartItemsResponse getCartItems(String cartId) throws ResourceNotFoundException;

	CartItemsResponse checkoutCart(String cartId, String couponId);
}
