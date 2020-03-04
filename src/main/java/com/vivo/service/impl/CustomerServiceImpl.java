package com.vivo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivo.entity.Customer;
import com.vivo.repository.CustomerRepository;
import com.vivo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository repository;
	@Override
	public Customer save(Customer c) {
		// TODO Auto-generated method stub
		return repository.save(c);
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmailEquals(email);
	}

}
