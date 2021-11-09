package com.hashkart.cartmicroservice.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.google.gson.GsonBuilder;
import com.hashkart.cartmicroservice.response.ProductResponse;

public class ProductResponseKeyDeserializer extends KeyDeserializer {

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
		return new GsonBuilder().create().fromJson(key, ProductResponse.class);
	}

}
