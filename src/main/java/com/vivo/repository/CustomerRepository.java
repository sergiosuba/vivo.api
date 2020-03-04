package com.vivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findByEmailEquals(String email);
}
