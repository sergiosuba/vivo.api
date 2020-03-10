package com.vivo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivo.entity.Account;
import com.vivo.repository.AccountRepository;
import com.vivo.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository repository;
	
	@Override
	public Account save(Account w) {
		// TODO Auto-generated method stub
		return repository.save(w);
	}

}
