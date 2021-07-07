package com.inspo.solutions.security;

import java.util.Optional;

public interface ApplicationUserDao {
	public Optional<ApplicationUser> selectApplicationUserByusername(String username);
}
