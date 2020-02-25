package com.employee.api.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.api.config.SecurityProps;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private SecurityProps configProperties;

	public Mono<User> findByUsername(String username) {
		String authAdminUsername = configProperties.getAdminUsername();
		String authAdminEncryptedPassword = configProperties.getAdminEncryptedPassword();
		User admin = new User(authAdminUsername, authAdminEncryptedPassword, true, Arrays.asList(Role.ROLE_ADMIN));

		if (username.equals(authAdminUsername)) {
			return Mono.just(admin);
		} else {
			return Mono.empty();
		}
	}

}