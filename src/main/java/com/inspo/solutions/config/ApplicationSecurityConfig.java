package com.inspo.solutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/","index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(ApplicationRole.STUDENT.name())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		UserDetails abhikuser = User.builder()
				.username("abhik")
				.password(passwordEncoder.encode("abhik"))
				.roles(ApplicationRole.ADMIN.name())
				.build();
		
		UserDetails jaysen = User.builder()
				.username("jaysen")
				.password(passwordEncoder.encode("jaysen"))
				.roles(ApplicationRole.STUDENT.name())
				.build();
		return new InMemoryUserDetailsManager(abhikuser, jaysen);
	}

	
	
}
