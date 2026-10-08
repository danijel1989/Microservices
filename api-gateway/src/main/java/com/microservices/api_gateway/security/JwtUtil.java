package com.microservices.api_gateway.security;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY = "my-super-secret-key-that-is-long-enough-for-hmac-sha256";
	
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith(key)
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
	
	public String extractUsername(String token) {
		return this.extractAllClaims(token).getSubject();
	}
	
	public boolean isTokenValid(String token) {
		
		try {
			this.extractAllClaims(token);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
