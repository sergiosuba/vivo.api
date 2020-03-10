package com.vivo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivo.entity.UserAccount;
import com.vivo.repository.UserAccountRepository;
import com.vivo.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService{

	@Autowired
	UserAccountRepository repository;
	
	@Override
	public UserAccount save(UserAccount uw) {
		return repository.save(uw);
	}

	@Override
	public Optional<UserAccount> findByUsersIdAndAccountId(Long user, Long account) {
		return repository.findByUsersIdAndAccountId(user, account);
	}

}
