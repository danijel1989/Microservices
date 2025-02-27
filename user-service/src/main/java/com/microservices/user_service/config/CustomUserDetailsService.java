package com.microservices.user_service.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.microservices.user_service.dao.UserDao;
import com.microservices.user_service.dto.UserDto;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDto> user = Optional.of(userDao.getUserByUsername(username));
		return user.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username "+username));
	}
	
	

}
