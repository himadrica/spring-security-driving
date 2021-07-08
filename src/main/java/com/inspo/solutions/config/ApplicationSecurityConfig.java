package com.inspo.solutions.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inspo.solutions.jwt.JWTTokenVerificationFilter;
import com.inspo.solutions.jwt.JWTusernameAndPasswordAuthenticationFilter;
import com.inspo.solutions.security.ApplicationUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final JWTConfig jwtConfig;
	private final SecretKey secretkey;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService,
			JWTConfig jwtConfig, SecretKey secretkey) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.jwtConfig = jwtConfig;
		this.secretkey = secretkey;
	}

	/*
	 *	Some note about logout, if you enable csfr, then use post for logout as recommended
	 *	If csfr is disabled, then you can use get. 
	 * 
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()			// recommendation: csfr is recommended for form based api submitted by browser, cross site request forgery
				.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(new JWTusernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretkey))
				.addFilterAfter(new JWTTokenVerificationFilter(jwtConfig,secretkey), JWTusernameAndPasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/","index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(ApplicationRole.STUDENT.name())
				.anyRequest()
				.authenticated();
					
				//.httpBasic(); basic auth enable just this line and remove other login type
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(applicationUserService);
		return daoAuthenticationProvider;
	}	
}
