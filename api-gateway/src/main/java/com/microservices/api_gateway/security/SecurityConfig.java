package com.microservices.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) {
		
		return http
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange -> exchange
													.pathMatchers("/auth/**").permitAll()
													.pathMatchers("/user-service/test_admin").hasRole("ADMIN")
													.anyExchange().authenticated())
				.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
		
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
		return new JwtAuthenticationFilter(jwtUtil);
	}

}
