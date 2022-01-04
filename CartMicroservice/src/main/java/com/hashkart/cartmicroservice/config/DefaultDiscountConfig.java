package com.hashkart.cartmicroservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "app.discount")
public class DefaultDiscountConfig {

	private Integer defaultDiscount;
	private Integer thresholdQuantity;
}
