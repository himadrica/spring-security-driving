package com.inspo.solutions.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.inspo.solutions.config.ApplicationRole;

@Repository("fake")
public class FakeApplicationUserDaoRepo implements ApplicationUserDao {

	private final PasswordEncoder passwordEncoder;
	
	
	public FakeApplicationUserDaoRepo(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByusername(String username) {
		
		return this.getApplicationUser()
				.stream()
				.filter(user -> user.getUsername().equalsIgnoreCase(username))
				.findFirst();
		
	}
	
	private List<ApplicationUser> getApplicationUser(){
		List<ApplicationUser> users = Lists.newArrayList(
				new ApplicationUser(
						"abhik",
						passwordEncoder.encode("abhik"),
						ApplicationRole.ADMIN.getGrantedAuthorities(),
						true,
						true,
						true,
						true
						),
				new ApplicationUser(
						"jaysen",
						passwordEncoder.encode("jaysen"),
						ApplicationRole.ASSISTANCE_ADMIN.getGrantedAuthorities(),
						true,
						true,
						true,
						true
						),
				new ApplicationUser(
						"arithra",
						passwordEncoder.encode("arithra"),
						ApplicationRole.STUDENT.getGrantedAuthorities(),
						true,
						true,
						true,
						true
						)
				);
		return users;
	}

}
