package com.vivo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Account;
import com.vivo.entity.AccountItem;
import com.vivo.repository.AccountItemRepository;
import com.vivo.service.AccountItemService;
import com.vivo.util.enums.TypeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountItemServiceTest {

	@MockBean
	AccountItemRepository repository;
	
	@Autowired
	AccountItemService service;
	
	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.DAT;
	private static final String DESCRIPTION = "Conta de Luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	
	@Test
	public void testSave() {
		BDDMockito.given(repository.save(Mockito.any(AccountItem.class))).willReturn(getMockAccountItem());

		AccountItem response = service.save(new AccountItem());
		
		assertNotNull(response);
		assertEquals(response.getDescription(), DESCRIPTION);
		assertEquals(response.getValue().compareTo(VALUE), 0);
	}
	
	@Test
	public void testFindBetweenDates() {
		List<AccountItem> list = new ArrayList<>();
		list.add(getMockAccountItem());
		Page<AccountItem> page = new PageImpl(list);
		
		BDDMockito.given(repository.findAllByAccountIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);
		
		Page<AccountItem> response = service.findBetweenDates(1L, new Date(), new Date(), 0);
		
		assertNotNull(response);
		assertEquals(response.getContent().size(), 1);
		assertEquals(response.getContent().get(0).getDescription(), DESCRIPTION);

	}
	
	@Test
	public void testFindByType() {
		List<AccountItem> list = new ArrayList<>();
		list.add(getMockAccountItem());
		
		BDDMockito.given(repository.findByAccountIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(list);
		
		List<AccountItem> response = service.findByAccountAndType(1L, TypeEnum.DAT);
		
		assertNotNull(response);
		assertEquals(response.get(0).getType(), TYPE);
	}
	
	@Test
	public void testSumByAccount() {
		BigDecimal value = BigDecimal.valueOf(45);

		BDDMockito.given(repository.sumByAccountId(Mockito.anyLong())).willReturn(value);
		
		BigDecimal response = service.sumByAccountId(1L);
		
		assertEquals(response.compareTo(value), 0);
	}
	
	private AccountItem getMockAccountItem() {
		Account w = new Account();
		w.setId(1L);
		
		AccountItem wi = new AccountItem(1L, w, DATE, TYPE, DESCRIPTION, VALUE);
		return wi;
	}
}
