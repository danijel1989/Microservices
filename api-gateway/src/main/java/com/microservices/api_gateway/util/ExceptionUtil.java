package com.microservices.api_gateway.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ExceptionUtil {
	
	public Mono<Void> handleRequest(ServerWebExchange exchange, HttpStatusCode statusCode, String message) {
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(statusCode);
		
		return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
	}

}
