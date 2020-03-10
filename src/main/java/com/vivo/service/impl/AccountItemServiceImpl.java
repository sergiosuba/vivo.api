package com.vivo.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.vivo.entity.AccountItem;
import com.vivo.repository.AccountItemRepository;
import com.vivo.service.AccountItemService;
import com.vivo.util.enums.TypeEnum;


@Service
public class AccountItemServiceImpl implements AccountItemService{

	@Autowired
	AccountItemRepository repository;
	
	@Value("${pagination.items_per_page}")
	private int itemsPerPage;
	
	@Override
	@CacheEvict(value = "findByAccountAndType", allEntries = true)
	public AccountItem save(AccountItem i) {
		return repository.save(i);
	}

	@Override
	public Page<AccountItem> findBetweenDates(Long account, Date start, Date end, int page) {
		
		@SuppressWarnings("deprecation")
		PageRequest pg = new PageRequest(page, itemsPerPage);
		
		return repository.findAllByAccountIdAndDateGreaterThanEqualAndDateLessThanEqual(account, start, end, pg);
	}

	@Override
	@Cacheable(value = "findByAccountAndType")
	public List<AccountItem> findByAccountAndType(Long account, TypeEnum type) {
		return repository.findByAccountIdAndType(account, type);
	}

	@Override
	public BigDecimal sumByAccountId(Long account) {
		return repository.sumByAccountId(account);
	}

	@Override
	public Optional<AccountItem> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@CacheEvict(value = "findByAccountAndType", allEntries = true)
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
