package com.hashkart.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hashkart.apigateway.domain.AuthUserPrincipal;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class HashkartZuulFilter extends ZuulFilter {

	@Autowired
	private AuthUserPrincipal authUserPrincipal;
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.addZuulRequestHeader("userId", authUserPrincipal.getUsername());
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
