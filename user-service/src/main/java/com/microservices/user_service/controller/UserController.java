package com.microservices.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user_service.dto.ProductDto;
import com.microservices.user_service.dto.UserDto;
//import com.microservices.user_service.dto.ProductDto;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.service.UserService;

import jakarta.ws.rs.Path;

@RestController
@RequestMapping(value = "/user-service")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/test")
	public String testMethod() {
		return "Hello from USER service test method";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createUser(@RequestBody User user) {
		userService.createUser(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User updatedUser, @PathVariable int id) {
		userService.updateUser(updatedUser, id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUserById(@PathVariable int id) {
		userService.deleteUserById(id);
	}
	
	@RequestMapping(value = "/getUserByUsername/{username}", method = RequestMethod.GET)
	public UserDto getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}
	
	@RequestMapping(value = "/{userId}/assignProduct", method = RequestMethod.PUT)
	public void assignProductToUser(@PathVariable int userId, @RequestBody ProductDto productDto) {
		userService.assignProductToUser(userId, productDto);
	}
	
	
	
//	@RequestMapping(value = "/products")
//	public List<ProductDto> getAllProducts() {
//		return userService.getAllProducts();
//	}

}
