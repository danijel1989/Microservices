package com.microservices.product_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.product_service.entity.Product;
import com.microservices.product_service.service.ProductService;

@RestController
@RequestMapping(value = "/product-service")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/test")
	public String testMethod() {
		return "Hello from PRODUCT service test method";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return productService.getProductList();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Product getProductById(@PathVariable int id) {
		return productService.getProductById(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createProduct(@RequestBody Product product) {
		productService.createProduct(product);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateProduct(@RequestBody Product product, @PathVariable int id) {
		productService.updateProduct(product, id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable int id) {
		productService.deleteProductById(id);
	}
	
	

}
