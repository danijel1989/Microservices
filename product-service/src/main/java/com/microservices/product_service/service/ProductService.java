package com.microservices.product_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.product_service.dao.ProductDao;
import com.microservices.product_service.entity.Product;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public List<Product> getProductList() {
		return productDao.getAllProducts();	
	}
	
	@Transactional
	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}
	
	@Transactional
	public void createProduct(Product product) {
		productDao.createProduct(product);
	}
	
	@Transactional
	public void updateProduct(Product product, int id) {
		productDao.updateProduct(product, id);
	}
	
	@Transactional
	public void deleteProductById(int id) {
		productDao.deleteProductById(id);
	}
}
