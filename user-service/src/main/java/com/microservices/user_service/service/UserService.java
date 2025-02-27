package com.microservices.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.user_service.client.ProductServiceClient;
import com.microservices.user_service.dao.UserDao;
import com.microservices.user_service.dto.ProductDto;
import com.microservices.user_service.dto.UserDto;
//import com.microservices.user_service.dto.ProductDto;
import com.microservices.user_service.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
//	REST TEMPLATE
//	@Autowired
//	private RestTemplate restTemplate;
//	@Value("${product-service.name}")
//	private String productServiceName;
//	@Value("${product-service.path}")
//	private String productServicePath;
	@Autowired
	private ProductServiceClient productServiceClient;
	@Autowired
	private UserDao userDao;
	

	@Transactional
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	@Transactional
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	@Transactional
	public void createUser(User user) {
		userDao.createUser(user);
	}
	
	@Transactional
	public void updateUser(User user, int id) {
		userDao.updateUser(user, id);
	}
	
	@Transactional
	public void deleteUserById(int id) {
		userDao.deleteUserById(id);
	}
	
	@Transactional
	public UserDto getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	@Transactional
	public void assignProductToUser(int userId, ProductDto productDto) {
		User user = this.getUserById(userId);
//		String productUrl = "http://"+productServiceName+productServicePath+productDto.getId();
//		ResponseEntity<ProductDto> product = restTemplate.getForEntity(productUrl, ProductDto.class);
		
		ProductDto product = productServiceClient.getProductById(productDto.getId());
		
		user.setProductId(product.getId());
		userDao.updateUser(user, userId);
	}
	
	
	
//	public List<User> getUsers() {
//		
//		List<User> list = new ArrayList<User>();
////		list.add(new User("Danijel", "Mihailovic", 35));
////		list.add(new User("Mina", "Nikolic", 31));
////		list.add(new User("Marko", "Marinkovic", 32));
//		
//		return list;
//		
//	}
	
//	public List<ProductDto> getAllProducts() {
//		String url = "http://PRODUCT-SERVICE/product-service/products";
//		ResponseEntity<List<ProductDto>> response =  restTemplate.exchange(
//				url, 
//				HttpMethod.GET, 
//				null, 
//				new ParameterizedTypeReference<List<ProductDto>>() {}
//				);
//		
//		return response.getBody();
//	}

}
