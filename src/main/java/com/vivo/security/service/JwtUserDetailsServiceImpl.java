package com.vivo.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vivo.entity.User;
import com.vivo.security.JwtUserFactory;
import com.vivo.service.UserService;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userService.findByEmail(email);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}