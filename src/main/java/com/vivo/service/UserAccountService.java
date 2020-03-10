package com.vivo.service;

import java.util.Optional;

import com.vivo.entity.UserAccount;

public interface UserAccountService {

	UserAccount save(UserAccount uw);
	
	Optional<UserAccount> findByUsersIdAndAccountId(Long user, Long account);
}
