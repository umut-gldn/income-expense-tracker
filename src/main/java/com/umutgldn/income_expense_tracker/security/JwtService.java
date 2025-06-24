package com.umutgldn.income_expense_tracker.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.umutgldn.income_expense_tracker.model.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	
	private final Key key;
	private final long expiration;
	
	public JwtService(@Value("${jwt.secret}") String secret,
					@Value("${jwt.expiration}") long expiration) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expiration=expiration;
	}
	
	
	public String generateToken(User user) {
		Date now=new Date();
		Date expiryDate=new Date(now.getTime()+expiration);
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(key)
				.compact();
	}
	
	
	public String extractUsername(String token){
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
	}
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("JWT error"+ e.getMessage());
			return false;
		}
	}
	
	

}
