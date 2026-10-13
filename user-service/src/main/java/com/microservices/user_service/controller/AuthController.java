package com.microservices.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.user_service.dao.UserDao;
import com.microservices.user_service.dto.LoginRequest;
import com.microservices.user_service.dto.RegisterRequest;
import com.microservices.user_service.dto.RegisterResponse;
import com.microservices.user_service.dto.UserDto;
import com.microservices.user_service.security.JwtUtil;
import com.microservices.user_service.service.UserService;



@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	private final JwtUtil jwtUtil;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserDao userDao;
	
	private AuthController(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder, UserDao userDao) {
		this.jwtUtil = jwtUtil;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.userDao = userDao;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody LoginRequest loginRequest) {
		
		UserDto userDto = userService.getUserByUsername(loginRequest.getUsername());
		boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword());

		if(passwordMatch) {
			return jwtUtil.generateToken(userDto.getUsername(), userDto.getRoles());
		}
		return "Invalid username or password";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
		
		if(userDao.checkIfUsernameExist(registerRequest.getUsername())) {
			return	ResponseEntity.status(HttpStatus.BAD_REQUEST)
						  .body(RegisterResponse.builder()
					                            .message("Username "+registerRequest.getUsername()+ "already exists, choose another one.")
					                            .build());
		}
		else {
			RegisterResponse response =  userService.registerNewUser(registerRequest);
			return ResponseEntity.status(HttpStatus.CREATED)
							     .body(response);
		}
	}

}
