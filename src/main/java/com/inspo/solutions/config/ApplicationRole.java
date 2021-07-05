package com.inspo.solutions.config;

import static com.inspo.solutions.config.ApplicationPermission.COURSE_READ;
import static com.inspo.solutions.config.ApplicationPermission.COURSE_WRITE;
import static com.inspo.solutions.config.ApplicationPermission.STUDENT_READ;
import static com.inspo.solutions.config.ApplicationPermission.STUDENT_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationRole {
	STUDENT(Sets.newHashSet()), 
	ASSISTANCE_ADMIN(Sets.newHashSet(COURSE_READ, STUDENT_READ)),
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

	private final Set<ApplicationPermission> permissions;

	ApplicationRole(Set<ApplicationPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationPermission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions = getPermissions()
											.stream()
											.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
											.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}

}
