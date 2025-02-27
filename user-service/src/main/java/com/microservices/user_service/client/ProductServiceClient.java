package com.microservices.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.user_service.dto.ProductDto;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
	
	@GetMapping(value =  "/product-service/{id}")
	ProductDto getProductById(@PathVariable int id);

}
