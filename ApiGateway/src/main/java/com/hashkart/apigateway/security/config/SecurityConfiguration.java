package com.hashkart.apigateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/cart/*").hasRole("API")
			.antMatchers("/cart/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/coupon/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, "/products/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/products").permitAll()
			.antMatchers(HttpMethod.GET, "/products/**").permitAll()
			.antMatchers(HttpMethod.GET, "/users/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/users/**").hasRole("API")
			.antMatchers("/login", "/signup").permitAll()
			.anyRequest().hasRole("ADMIN")
			.and()
			.exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
			.formLogin()
			.successHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
            .failureHandler(new SimpleUrlAuthenticationFailureHandler())
            .and()
            .logout()
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
	}
}
