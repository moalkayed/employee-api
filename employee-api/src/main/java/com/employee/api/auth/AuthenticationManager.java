package com.employee.api.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	@Autowired
	private JWTUtil jwtUtil;

	@SuppressWarnings("unchecked")
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();

		String username;
		try {
			username = jwtUtil.getUsernameFromToken(authToken);
		} catch (Exception e) {
			// TODO disable/enable Authorization token
			// username = null;
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin", null,
					Arrays.asList(Role.ROLE_ADMIN).stream()
							.map(authority -> new SimpleGrantedAuthority(authority.name()))
							.collect(Collectors.toList()));
			return Mono.just(auth);
		}
		if (username != null && jwtUtil.validateToken(authToken)) {
			Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
			List<String> rolesMap = claims.get("role", List.class);
			List<Role> roles = new ArrayList<>();
			for (String rolemap : rolesMap) {
				roles.add(Role.valueOf(rolemap));
			}
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
					roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name()))
							.collect(Collectors.toList()));
			return Mono.just(auth);
		} else {
			return Mono.empty();
		}
	}
}