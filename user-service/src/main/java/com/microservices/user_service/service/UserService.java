package com.microservices.user_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.microservices.user_service.client.ProductServiceClient;
import com.microservices.user_service.dao.RoleDao;
import com.microservices.user_service.dao.UserDao;
import com.microservices.user_service.dto.ProductDto;
import com.microservices.user_service.dto.RegisterRequest;
import com.microservices.user_service.dto.RegisterResponse;
import com.microservices.user_service.dto.UserDto;
import com.microservices.user_service.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final ProductServiceClient productServiceClient;
	private final UserDao userDao;
	private final RoleDao roleDao;


	UserService(PasswordEncoder passwordEncoder, ProductServiceClient productServiceClient,
			    UserDao userDao, RoleDao roleDao) {
		this.passwordEncoder = passwordEncoder;
		this.productServiceClient = productServiceClient;
		this.userDao = userDao;
		this.roleDao = roleDao;
	}
	

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
		ProductDto product = productServiceClient.getProductById(productDto.getId());
		user.setProductId(product.getId());
		userDao.updateUser(user, userId);
	}

	@Transactional
	public RegisterResponse registerNewUser(RegisterRequest registerRequest) {
		
		User newUser = new User();
		newUser.setFirstName(registerRequest.getFirstName());
		newUser.setLastName(registerRequest.getLastName());
		newUser.setUsername(registerRequest.getUsername());
		newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		newUser.setAge(registerRequest.getAge());
		newUser.getRoles().add(roleDao.findByName("USER"));
		userDao.createUser(newUser);
		
		return RegisterResponse.builder().firstName(registerRequest.getFirstName())
				 .lastName(registerRequest.getLastName())
				 .username(registerRequest.getUsername())
				 .message("Welcome " + registerRequest.getFirstName() + "! Your username is " + registerRequest.getUsername())
				 .build();
		
	}
	


}
