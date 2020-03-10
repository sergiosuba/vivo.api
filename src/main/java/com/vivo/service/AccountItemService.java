package com.vivo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.vivo.entity.AccountItem;
import com.vivo.util.enums.TypeEnum;

public interface AccountItemService {

	AccountItem save(AccountItem i);
	
	Page<AccountItem> findBetweenDates(Long account, Date start, Date end, int page);
	
	List<AccountItem> findByAccountAndType(Long account, TypeEnum type);
	
	BigDecimal sumByAccountId(Long account);
	
	Optional<AccountItem> findById(Long id);
	
	void deleteById(Long id);
}
