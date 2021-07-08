package com.inspo.solutions.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;
import com.inspo.solutions.config.JWTConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTTokenVerificationFilter extends OncePerRequestFilter {
	
	private final JWTConfig jwtConfig;
	private final SecretKey secretkey;
	
	
	public JWTTokenVerificationFilter(JWTConfig jwtConfig, SecretKey secretkey) {
		this.jwtConfig = jwtConfig;
		this.secretkey = secretkey;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationheader = request.getHeader(jwtConfig.getAuthorizationHeader());
		String token = "";
		if (Strings.isNullOrEmpty(authorizationheader) || !authorizationheader.startsWith(jwtConfig.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			token = authorizationheader.replace(jwtConfig.getTokenPrefix(), "");
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretkey).build()
					.parseClaimsJws(token);

			Claims body = claimsJws.getBody();
			String username = body.getSubject();

			@SuppressWarnings("unchecked")
			var authorities = (List<Map<String, String>>) body.get("authorities");

			Set<SimpleGrantedAuthority> simpleAuthorities = authorities.stream()
					.map(auth -> new SimpleGrantedAuthority(auth.get("authority"))).collect(Collectors.toSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException e) {
			throw new IllegalStateException("Token can not be trusted" + token);
		}
		
		filterChain.doFilter(request, response);

	}

}
