package com.hashkart.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.paths(PathSelectors.ant("/cart/**"))
//				.apis(RequestHandlerSelectors.basePackage("com.hashkart.cartmicroservice"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfoBuilder()
				.title("API Gateway")
				.description("Gateway service for Hashkart")
				.contact(new Contact("Akshay Kataria", "", "akshaykataria03@gmail.com"))
				.version("1.0")
				.build();
	}
}
