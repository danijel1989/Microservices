package com.microservices.user_service.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.microservices.user_service.dto.UserDto;
import com.microservices.user_service.entity.User;

public class CustomUserDetails implements UserDetails {
	
	private String username;
	private String password;
	
	public CustomUserDetails(UserDto user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	

}
