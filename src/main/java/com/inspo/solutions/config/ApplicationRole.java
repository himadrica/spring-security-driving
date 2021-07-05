package com.inspo.solutions.config;

import java.util.Set;
import com.google.common.collect.*;
import static com.inspo.solutions.config.ApplicationPermission.*;

public enum ApplicationRole {
	STUDENT(Sets.newHashSet()), 
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

	private final Set<ApplicationPermission> permissions;

	ApplicationRole(Set<ApplicationPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationPermission> getPermissions() {
		return permissions;
	}

}
