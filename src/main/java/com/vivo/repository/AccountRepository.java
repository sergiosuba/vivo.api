package com.vivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
