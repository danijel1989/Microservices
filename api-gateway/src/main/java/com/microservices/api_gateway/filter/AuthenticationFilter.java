package com.microservices.api_gateway.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.microservices.api_gateway.util.ExceptionUtil;
import com.microservices.api_gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	private RouteValidator validator;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	@Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                	
                return exceptionUtil.handleRequest(exchange, HttpStatus.BAD_REQUEST, "No authorization header");	
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);

                } catch (Exception e) {
                	return exceptionUtil.handleRequest(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }
	
	
	
	public static class Config {
		
	}

}
