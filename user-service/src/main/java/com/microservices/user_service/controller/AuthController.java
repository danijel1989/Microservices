package com.microservices.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user_service.dto.LoginRequest;
import com.microservices.user_service.dto.UserDto;
import com.microservices.user_service.security.JwtUtil;
import com.microservices.user_service.service.UserService;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody LoginRequest loginRequest) {
		
		UserDto userDto = userService.getUserByUsername(loginRequest.getUsername());
		
		boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword());
		
		if(passwordMatch) {
			return jwtUtil.generateToken(userDto.getUsername(), userDto.getRoles());
		}
		return "Invalid username or password";
	}

}
