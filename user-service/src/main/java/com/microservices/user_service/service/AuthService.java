package com.microservices.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.user_service.dao.UserDao;
import com.microservices.user_service.entity.User;

@Service
public class AuthService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	
	public String saveNewUser(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userDao.createUser(newUser);
		return "New user added";
	}
	
	public String generateToken(String username) {
		return jwtService.generateToken(username);
	}
	
	public void validateToken(String token) {
		jwtService.validateToken(token);
	}
	
	

}
