package com.hashkart.apigateway.security.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashkart.apigateway.security.filter.TokenAuthenticationFilter;
import com.hashkart.commonutilities.response.ErrorResponse;
import com.hashkart.commonutilities.response.GenericApiResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String UNAUTHORIZED = "UNAUTHORIZED";
	private static final String BAD_CREDENTIALS = "BAD_CREDENTIALS";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;
	
	@Autowired
	private ObjectMapper objectMapper;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/auth/**", "/actuator/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-ui/**", "/v2/api-docs").permitAll()
			.antMatchers(HttpMethod.GET, "/products", "/products/**").permitAll()
			.antMatchers("/cart/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/coupon/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, "/products/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/users/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/users/**").hasRole("API")
			.anyRequest().hasRole("ADMIN")
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.accessDeniedHandler(this.accessDeniedHandler())
			.authenticationEntryPoint(this.authenticationEntryPoint())
			.and()
			.addFilterBefore(this.tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	private AuthenticationEntryPoint authenticationEntryPoint() {
		return (httpServletRequest, httpServletResponse, e) -> {
			System.out.println(e.getMessage());
			GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
			apiResponse.setError(new ErrorResponse(BAD_CREDENTIALS, "Not Authenticated", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()));
			String responseBody = this.objectMapper.writeValueAsString(apiResponse);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpServletResponse.getWriter().append(responseBody);
            httpServletResponse.setStatus(401);
		};
	}
	
	private AccessDeniedHandler accessDeniedHandler() {
		return (httpServletRequest, httpServletResponse, e) -> {
			System.out.println(e.getMessage());
			GenericApiResponse<?> apiResponse = new GenericApiResponse<>();
			apiResponse.setError(new ErrorResponse(UNAUTHORIZED, "Access Denied", HttpStatus.FORBIDDEN.value(), LocalDateTime.now()));
			String responseBody = this.objectMapper.writeValueAsString(apiResponse);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpServletResponse.getWriter().append(responseBody);
            httpServletResponse.setStatus(403);
		}; 
	}
}
