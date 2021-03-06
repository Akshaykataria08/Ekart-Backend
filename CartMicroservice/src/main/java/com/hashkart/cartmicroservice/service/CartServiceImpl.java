package com.hashkart.cartmicroservice.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashkart.cartmicroservice.config.DefaultDiscountConfig;
import com.hashkart.cartmicroservice.dao.CartRepository;
import com.hashkart.cartmicroservice.domain.Cart;
import com.hashkart.cartmicroservice.response.CartItemsResponse;
import com.hashkart.cartmicroservice.response.CouponResponse;
import com.hashkart.cartmicroservice.response.ProductListResponse;
import com.hashkart.cartmicroservice.response.ProductResponse;
import com.hashkart.commonutilities.exception.BadRequestException;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.commonutilities.response.GenericApiResponse;

@RefreshScope
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DefaultDiscountConfig defaultDiscountConfig;

	@Autowired
	private HttpConnectorService httpService;

	@Value("${productServiceUrl}")
	private String PRODUCT_MICROSERVICE;
	@Value("${productServiceBasePath}")
	private String UPDATE_PRODUCT_QUANTITY_PATH;
	@Value("${productServiceBulkUpdatePath}")
	private String BULK_PRODUCTS_PATH;
	@Value("${couponServiceUrl}")
	private String COUPON_MICROSERVICE;
	@Value("${couponServicePath}")
	private String GET_COUPON_PATH;

	@Override
	public Cart createCart(String cartId) throws ResourceAlreadyExistsException {
		System.out.println("Create Cart called.");
		if (cartRepo.existsById(cartId)) {
			throw new ResourceAlreadyExistsException("Cart for Id " + cartId + " already exists");
		}
		return cartRepo.save(new Cart(cartId, new LinkedHashMap<>(), false, 0.0, 0, 0.0));
	}

	@Override
	public CartItemsResponse addProductToCart(String cartId, Long productId) throws ResourceNotFoundException {
		Cart cart = this.getCartOrThrowException(cartId);
		cart.setCheckout(false);
		Optional<Integer> productQuantity = Optional.ofNullable(cart.getProductQuantities().get(productId));
		updateProductInInventory(productId, productQuantity.orElse(0)+1, productQuantity.orElse(0));
		cart.getProductQuantities().put(productId, productQuantity.orElse(0) + 1);
		CartItemsResponse response = getCartItems(cart);
		cartRepo.save(cart);
		return response;
	}

	@Override
	public CartItemsResponse updateProductQuantity(String cartId, Long productId, Integer quantity)
			throws BadRequestException, ResourceNotFoundException {
		if (quantity < 0) {
			throw new BadRequestException("Quantity should not be less than 0");
		}
		Cart cart = this.getCartOrThrowException(cartId);
		cart.setCheckout(false);
		if (!cart.getProductQuantities().containsKey(productId)) {
			throw new ResourceNotFoundException("No Product with Id " + productId + " exists in the cart");
		}
		updateProductInInventory(productId, quantity, cart.getProductQuantities().get(productId));
		if (quantity == 0) {
			cart.getProductQuantities().remove(productId);
		} else {
			cart.getProductQuantities().put(productId, quantity);
		}
		CartItemsResponse response = getCartItems(cart);
		cartRepo.save(cart);
		return response;
	}

	@Override
	public CartItemsResponse removeProductFromCart(String cartId, Long productId) throws ResourceNotFoundException {
		return this.updateProductQuantity(cartId, productId, 0);
	}

	@Override
	public CartItemsResponse getCartItems(String cartId) throws ResourceNotFoundException {
		return getCartItems(this.getCartOrThrowException(cartId));
	}

	@Override
	public CartItemsResponse checkoutCart(String cartId, String couponId) {
		Cart cart = this.getCartOrThrowException(cartId);
		CartItemsResponse response = getCartItems(cart);
		Integer couponDiscount = getCouponDiscount(couponId);
		double cartTotal = calculateCartTotal(response.getProducts(),
				defaultDiscountConfig.getThresholdQuantity(), defaultDiscountConfig.getDefaultDiscount());
		double discountedCartTotal = calculateDiscountPrice(cartTotal, couponDiscount);
		cart.setCartTotal(cartTotal);
		cart.setCouponDiscount(couponDiscount);
		cart.setDiscountedCartTotal(discountedCartTotal);
		cart.setCheckout(true);
		cartRepo.save(cart);
		return new CartItemsResponse(cartId, response.getProducts(), cartTotal,
				couponDiscount, discountedCartTotal);
	}
	
	private void updateProductInInventory(Long productId, int newQuantity, int oldQuantity) {

		String url = UriComponentsBuilder.fromHttpUrl(PRODUCT_MICROSERVICE + UPDATE_PRODUCT_QUANTITY_PATH + "/" + productId).build().toUri()
				.toString();
		ProductResponse product = new ProductResponse();
		product.setQuantity(newQuantity - oldQuantity);
		JsonNode jsonResponse = httpService.put(url, product, JsonNode.class);
		objectMapper.convertValue(jsonResponse,
				new TypeReference<GenericApiResponse<ProductResponse>>() {
				});
	}
	
	private Integer getCouponDiscount(String couponId) {
		if(couponId == null) {
			return 0;
		}
		String url = UriComponentsBuilder.fromHttpUrl(COUPON_MICROSERVICE + GET_COUPON_PATH + "/" + couponId).build().toUri().toString();
		JsonNode jsonResponse = httpService.get(url, JsonNode.class);
		return objectMapper.convertValue(jsonResponse,
				new TypeReference<GenericApiResponse<CouponResponse>>() {
				}).getData().getCouponDiscount();
	}

	private Cart getCartOrThrowException(String cartId) throws ResourceNotFoundException {
		return cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("No Cart has been created for id " + cartId));
	}

	private CartItemsResponse getCartItems(Cart cart) {
		Set<Long> productIds = cart.getProductQuantities().keySet();
		List<ProductResponse> products = fetchProductsByIds(productIds);
		populateRequestedQuantity(products, cart.getProductQuantities());
		double cartTotal = calculateCartTotal(products, defaultDiscountConfig.getThresholdQuantity(),
				defaultDiscountConfig.getDefaultDiscount());
		CartItemsResponse response = new CartItemsResponse(cart.getCartId(), products, cartTotal, cart.getCouponDiscount(), calculateDiscountPrice(cartTotal, cart.getCouponDiscount()));
		cart.getProductQuantities().entrySet()
		.removeIf(entry -> !response.getProducts().contains(new ProductResponse(entry.getKey())));
		return response;
	}

	private List<ProductResponse> fetchProductsByIds(Set<Long> productIds) {
		String url = UriComponentsBuilder.fromHttpUrl(PRODUCT_MICROSERVICE + BULK_PRODUCTS_PATH)
				.queryParam("productIds", productIds).build().toUri().toString();

		JsonNode jsonResponse = httpService.get(url, JsonNode.class);
		GenericApiResponse<ProductListResponse> productMicroserviceResponse = objectMapper.convertValue(jsonResponse,
				new TypeReference<GenericApiResponse<ProductListResponse>>() {
				});

		return productMicroserviceResponse.getData().getProducts();
	}

	private double calculateCartTotal(List<ProductResponse> products, Integer thresholdQuantity,
			Integer defaultDiscount) {
		return products.stream().map(product -> {
			if (product.getRequestedQuantity() < thresholdQuantity) {
				product.setDefaultDiscountApplied(0);
			} else {
				product.setDefaultDiscountApplied(defaultDiscount);
			}
			double discountedPrice = calculateDiscountPrice(product.getPrice() * product.getRequestedQuantity(), product.getDefaultDiscountApplied());
			product.setDiscountedPrice(discountedPrice);
			return discountedPrice;
		}).reduce(0.0, (price1, price2) -> price1 + price2);
	}

	private double calculateDiscountPrice(Double price, Integer defaultDiscount) {
		return (100 - defaultDiscount) * price / 100;
	}

	private void populateRequestedQuantity(List<ProductResponse> products, Map<Long, Integer> productQuantities) {
		products.forEach(product -> {
			product.setRequestedQuantity(productQuantities.get(product.getProductId()));
		});
	}
}
