package com.hashkart.cartmicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.hashkart.cartmicroservice.dao.CartRepository;
import com.hashkart.cartmicroservice.domain.Cart;
import com.hashkart.cartmicroservice.response.ProductListResponse;
import com.hashkart.cartmicroservice.response.ProductResponse;
import com.hashkart.commonutilities.exception.ResourceAlreadyExistsException;
import com.hashkart.commonutilities.exception.ResourceNotFoundException;
import com.hashkart.commonutilities.response.GenericApiResponse;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	@Mock
	private CartRepository cartRepo;

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private HttpConnectorService httpService;
	
	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private CartServiceImpl cartService;
	
	@BeforeEach
	public void setUp() {
		objectMapper.registerModule(new JSR310Module());
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyy'T'HH:mm:ss.SSS"));
	}
	
	@Test
	public void testCreateCartWithValidId() {
		String cartId = "sampleId";
		when(cartRepo.existsById(cartId)).thenReturn(false);
		Cart cart = new Cart();
		cart.setCartId(cartId);
		when(cartRepo.save(any(Cart.class))).thenReturn(cart);
		Cart actualCart = cartService.createCart(cartId);
		assertEquals(cart.getCartId(), actualCart.getCartId());
	}
	
	@Test
	public void testCreateCartWithInValidId() {
		String cartId = "sampleId";
		when(cartRepo.existsById(cartId)).thenReturn(true);
		assertThrows(ResourceAlreadyExistsException.class, () -> cartService.createCart(cartId));
	}
	
	@Test
	public void testAddProductToCartWhereCartDoesNotExists() {
		String cartId = "sampleId";
		Optional<Cart> cart = Optional.empty();
		when(cartRepo.findById(cartId)).thenReturn(cart);
		assertThrows(ResourceNotFoundException.class, () -> cartService.addProductToCart(cartId, any(Long.class)));
	}
	
	@Disabled
	@Test
	public void testAddProductToCartValidScenario() {
		String cartId = "sampleId";
		Long productId = 12345l;
		List<Long> productIds = new ArrayList<>();
		productIds.add(productId);
		Cart cart = new Cart();
		cart.setCartId(cartId);
		String updateInventoryPath = "http://localhost:8002/products/" + productId;
		Optional<Cart> optionalCart = Optional.of(cart);
		when(cartRepo.findById(cartId)).thenReturn(optionalCart);
		when(httpService.put(eq(updateInventoryPath), any(ProductResponse.class), eq(JsonNode.class))).thenReturn(getMockJsonProductResponse(productId));
		when(httpService.get(any(), JsonNode.class)).thenReturn(getMockJsonProductListResponse(productIds));
		assertTrue(cartService.addProductToCart(cartId, productId).getProducts().contains(getMockProductResponse(productId)));
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	private JsonNode getMockJsonProductResponse(Long productId) {
		GenericApiResponse<ProductResponse> response = new GenericApiResponse<>();
		response.setData(getMockProductResponse(productId));
		return objectMapper.convertValue(response, JsonNode.class);
	}
	
	private ProductResponse getMockProductResponse(Long productId) {
		ProductResponse mockProductResponse = new ProductResponse();
		mockProductResponse.setProductId(productId);
		return mockProductResponse;
	}
	
	private JsonNode getMockJsonProductListResponse(List<Long> list) {
		GenericApiResponse<ProductListResponse> response = new GenericApiResponse<>();
		response.setData(getMockProductListResponse(list));
		return objectMapper.convertValue(response, JsonNode.class);
	}

	private ProductListResponse getMockProductListResponse(List<Long> list) {
		ProductListResponse productListResponse = new ProductListResponse();
		productListResponse.setProducts(list.stream().map(this::getMockProductResponse).collect(Collectors.toList()));
		return productListResponse;
	}
}
