package com.vivo.service;

import java.util.Optional;

import com.vivo.entity.User;

public interface UserService {

	User save(User u);
	
	Optional<User> findByEmail(String email);
}
