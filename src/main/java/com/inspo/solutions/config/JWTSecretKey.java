package com.inspo.solutions.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JWTSecretKey {
	
	private final JWTConfig jwtConfig;
	
	public JWTSecretKey(JWTConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	@Bean
	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
	}
}
