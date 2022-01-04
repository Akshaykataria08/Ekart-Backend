package com.hashkart.apigateway.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//@Component
public class UserPrincipalFilter implements Filter {

//	@Autowired
//	private AuthUserPrincipal authUserPrincipal;
//	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpSession session = httpRequest.getSession(false);
//		if(session != null) {
//			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
//			if (sci != null) {
//		        UserDetails userDetails = (UserDetails) sci.getAuthentication().getPrincipal();
//		        if(userDetails != null) {
//		        	authUserPrincipal.setUsername(userDetails.getUsername());
//		        }
//			}
//		}
		chain.doFilter(request, response);
	}
}
