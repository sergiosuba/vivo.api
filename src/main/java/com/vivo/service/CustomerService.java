package com.vivo.service;

import java.util.Optional;

import com.vivo.entity.Customer;

public interface CustomerService {
	
	Customer save(Customer c);
	
	Optional<Customer> findByEmail(String eamil);

}
