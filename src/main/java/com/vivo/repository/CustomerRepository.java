package com.vivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
