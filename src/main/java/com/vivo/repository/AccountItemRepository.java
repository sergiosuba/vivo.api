package com.vivo.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vivo.entity.AccountItem;
import com.vivo.util.enums.TypeEnum;

public interface AccountItemRepository extends JpaRepository<AccountItem, Long> {

	Page<AccountItem> findAllByAccountIdAndDateGreaterThanEqualAndDateLessThanEqual(Long account, Date init, Date end, Pageable page);
	
	List<AccountItem> findByAccountIdAndType(Long account, TypeEnum type);
	
	@Query(value = "select sum(value) from AccountItem wi where wi.account.id = :account")
	BigDecimal sumByAccountId(@Param("account") Long account);
}
