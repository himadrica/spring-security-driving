package com.inspo.solutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.authorizeRequests()
				.antMatchers("/","index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(ApplicationRole.STUDENT.name())
//				.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.PUT, "/management/api/**").hasAnyAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.POST, "/management/api/**").hasAnyAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationRole.ADMIN.name(), ApplicationRole.ASSISTANCE_ADMIN.name())
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
				//.roles(ApplicationRole.ADMIN.name())
				.authorities(ApplicationRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails jaysen = User.builder()
				.username("jaysen")
				.password(passwordEncoder.encode("jaysen"))
				//.roles(ApplicationRole.STUDENT.name())
				.authorities(ApplicationRole.ASSISTANCE_ADMIN.getGrantedAuthorities())
				.build();
		

		UserDetails adhishree = User.builder()
				.username("adhishree")
				.password(passwordEncoder.encode("adhishree"))
				//.roles(ApplicationRole.ASSISTANCE_ADMIN.name())
				.authorities(ApplicationRole.STUDENT.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(abhikuser, jaysen, adhishree);
	}

	
	
}
