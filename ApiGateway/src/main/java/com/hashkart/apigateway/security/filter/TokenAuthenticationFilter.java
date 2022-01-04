package com.hashkart.apigateway.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.hashkart.apigateway.domain.AuthUserPrincipal;
import com.hashkart.apigateway.security.dto.TokenBasedAuthentication;
import com.hashkart.apigateway.security.utility.TokenHelper;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthUserPrincipal authUserPrincipal;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = tokenHelper.getToken(request);
		if(!StringUtils.isBlank(token) && tokenHelper.tokenValid(token)) {
			String username = tokenHelper.getUsername(token);
			if(!StringUtils.isBlank(username)) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
				authentication.setToken(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				authUserPrincipal.setUsername(userDetails.getUsername());
			}	
		}
		filterChain.doFilter(request, response);
	}
}
