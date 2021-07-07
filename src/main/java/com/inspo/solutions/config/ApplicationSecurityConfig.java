package com.inspo.solutions.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.inspo.solutions.security.ApplicationUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
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
					.formLogin()
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/courses", true) // form based authentication enable just this line
					.passwordParameter("password")
					.usernameParameter("username")
				.and()
					.rememberMe() // it enables remember me, by setting sessionId expire to 2 weeks
					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
					.key("forhasingneedasecurekeytohashuserandexpiretime") 
					.rememberMeParameter("remember-me")
				.and()
					.logout()
					.logoutUrl("/logout")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // csfr is diabled then GET is ok
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID","remember-me")
					.logoutSuccessUrl("/login");
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
