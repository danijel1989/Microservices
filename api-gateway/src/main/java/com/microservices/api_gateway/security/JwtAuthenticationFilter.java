package com.microservices.api_gateway.security;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;


public class JwtAuthenticationFilter implements WebFilter{
	
	private final JwtUtil jwtUtil;
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		String path = exchange.getRequest()
							  .getURI()
							  .getPath();
		
		if(path.startsWith("/auth/")) {
			return chain.filter(exchange);
		}
		
		String authorizationHeader = exchange.getRequest()
									         .getHeaders()
									         .getFirst(HttpHeaders.AUTHORIZATION);
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String token = authorizationHeader.substring(7);
		
		if(!jwtUtil.isTokenValid(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String username = jwtUtil.extractUsername(token);
		
		Claims claims = jwtUtil.extractAllClaims(token);
		
		@SuppressWarnings("unchecked")
		List<String> roles = claims.get("roles", List.class);
		
		List<SimpleGrantedAuthority> authorities = roles.stream()
														.map(role -> new SimpleGrantedAuthority("ROLE_"+ role))
														.toList();
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
											 
		return chain.filter(exchange)
					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
	}
	
	

}
