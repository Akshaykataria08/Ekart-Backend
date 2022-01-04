package com.hashkart.apigateway.security.utility;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hashkart.apigateway.security.domain.HashkartUserDetails;

@Component
@RefreshScope
public class TokenHelper {

	@Value("${jwt.secret}")
	private String SECRET;
	
	@Value("${jwt.expires-in}")
	private String EXPIRES_IN;
	
	@Value("${jwt.header}")
	private String AUTH_HEADER;
	
	@Value("${jwt.issuer}")
	private String ISSUER;
	
	private static final String BEARER = "Bearer ";
	
	private JWTVerifier verifier;
	
	public String generateToken(UserDetails userDetails) {
		return JWT.create()
				.withAudience("web")
				.withSubject(((HashkartUserDetails) userDetails).getUsername())
				.withClaim("roles", ((HashkartUserDetails) userDetails)
						.getAuthorities().stream()
						.map(grantedAuth -> grantedAuth.getAuthority())
						.collect(Collectors.toList()))
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withIssuer(ISSUER)
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * Integer.parseInt(EXPIRES_IN)))
				.sign(this.getAlgorithm());
	}
	
	public String getToken(HttpServletRequest req) {
		String authHeader = req.getHeader(AUTH_HEADER);
		if(authHeader != null && authHeader.startsWith(BEARER)) {
			return authHeader.substring(BEARER.length());
		}
		return null;
	}
	
	public String getUsername(String token) {
		return JWT.decode(token).getSubject();
	}
	
	public boolean tokenValid(String token) {
		try {
			DecodedJWT jwt = this.getVerifier().verify(token);
			return !tokenExpired(jwt);
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean tokenExpired(DecodedJWT jwt) {
		return jwt.getExpiresAt().before(new Date(System.currentTimeMillis()));
	}
	
	private JWTVerifier getVerifier() {
		if(this.verifier == null) {
			this.verifier = JWT.require(getAlgorithm()).build();
		}
		return this.verifier;
	}
	
	private Algorithm getAlgorithm() {
		return Algorithm.HMAC512(SECRET);
	}
}
