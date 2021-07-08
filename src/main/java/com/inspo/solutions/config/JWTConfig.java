package com.inspo.solutions.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JWTConfig {

	private String secret;
	private String tokenPrefix;
	private Integer tokenExpirationAfterDays;

	public JWTConfig() {

	}

	public String getSecret() {
		return secret;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public Integer getTokenExpirationAfterDays() {
		return tokenExpirationAfterDays;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.tokenExpirationAfterDays = tokenExpirationAfterDays;
	}
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

}
