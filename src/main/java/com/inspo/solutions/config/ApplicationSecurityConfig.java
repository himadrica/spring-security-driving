package com.inspo.solutions.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()			// recommendation: csfr is recommended for form based api submitted by browser, cross site request forgery
				.disable()
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
				.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/courses", true) // form based authentication enable just this line
				.and()
				.rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)).key("forhasingneedasecurekeytohashuserandexpiretime").userDetailsService(userDetailsServiceBean()); // it enables remember me, by setting sessionId expire to 2 weeks
				//.httpBasic(); basic auth enable just this line and remove other login type
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
