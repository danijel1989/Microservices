package com.microservices.user_service.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final SecretKey key;
	
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(String username, List<String> roles) {
		
		return Jwts.builder()
				   .claim("roles", roles)
				   .subject(username)
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))
				   .signWith(key)
				   .compact();
	}

}
