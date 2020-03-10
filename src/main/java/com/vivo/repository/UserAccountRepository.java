package com.vivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivo.entity.UserAccount;

public interface UserAccountRepository  extends JpaRepository<UserAccount, Long>{

	Optional<UserAccount> findByUsersIdAndAccountId(Long user, Long account);
}
