package com.vivo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Account;
import com.vivo.entity.AccountItem;
import com.vivo.repository.AccountItemRepository;
import com.vivo.repository.AccountRepository;
import com.vivo.util.enums.TypeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountItemRepositoryTest {
	
	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.DAT;
	private static final String DESCRIPTION = "Dados da conta do Sergio Suba";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65); 
	private static final Number LINENUMBER = 988880099;
	private Long savedAccountItemId = null;
	private Long savedAccountId = null;
	
	@Autowired
	AccountItemRepository repository;
	@Autowired
	AccountRepository accountRepository;
	
	@Before
	public void setUp() {
		Account a = new Account();
		a.setName("Conta de Teste");
		a.setValue(BigDecimal.valueOf(250));
		a.setLineNumber(988880000);
		accountRepository.save(a);
		
		AccountItem wi = new AccountItem(null, a, DATE, TYPE, DESCRIPTION, VALUE);
		repository.save(wi);
		
		savedAccountItemId = wi.getId();
		savedAccountId = a.getId();
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
		accountRepository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		Account a = new Account();
		a.setName("Sergio Suba");
		a.setValue(BigDecimal.valueOf(500));
		a.setLineNumber(988880088);
		accountRepository.save(a);
		
		AccountItem wi = new AccountItem(1L, a, DATE, TYPE, DESCRIPTION, VALUE);
		
		AccountItem response = repository.save(wi);
		
		assertNotNull(response);
		assertEquals(response.getDescription(), DESCRIPTION);
		assertEquals(response.getType(), TYPE);
		assertEquals(response.getValue(), VALUE);
		assertEquals(response.getAccount().getId(), a.getId());
		
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveInvalidAccountItem() {
		AccountItem ai = new AccountItem(null, null, DATE, null, DESCRIPTION, null);
		repository.save(ai);
	}
	
	@Test
	public void testUpdate() {
		Optional<AccountItem> ai = repository.findById(savedAccountItemId);
		
		String description = "Descrição alterada";
		
		AccountItem changed = ai.get();
		changed.setDescription(description);
		
		repository.save(changed);
		
		Optional<AccountItem> newAccountItem = repository.findById(savedAccountItemId);
		
		assertEquals(description, newAccountItem.get().getDescription());
	}
	
	@Test
	public void deleteAccountItem() {
		Optional<Account> account = accountRepository.findById(savedAccountId);
		AccountItem ai = new AccountItem(null, account.get(), DATE, TYPE, DESCRIPTION, VALUE);
		
		repository.save(ai);
		
		repository.deleteById(ai.getId());
		
		Optional<AccountItem> response = repository.findById(ai.getId());
		
		assertFalse(response.isPresent());
	}
	
	@Test
	public void testFindBetweenDates() {
		Optional<Account> w = accountRepository.findById(savedAccountId);
		
		LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        
		repository.save(new AccountItem(null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
		repository.save(new AccountItem(null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));
		
		PageRequest pg = new PageRequest(0, 10);
		Page<AccountItem> response = repository.findAllByAccountIdAndDateGreaterThanEqualAndDateLessThanEqual(savedAccountId, DATE, currentDatePlusFiveDays, pg);
		
		assertEquals(response.getContent().size(), 2);
		assertEquals(response.getTotalElements(), 2);
		assertEquals(response.getContent().get(0).getAccount().getId(), savedAccountId);
	}
	
	@Test
	public void testFindByType() {
		List<AccountItem> response = repository.findByAccountIdAndType(savedAccountId, TYPE);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TYPE);
	}
	
	@Test
	public void testFindByTypeSMS() {
		
		Optional<Account> w = accountRepository.findById(savedAccountId);
		
		repository.save(new AccountItem(null, w.get(), DATE, TypeEnum.SMS, DESCRIPTION, VALUE));
		
		List<AccountItem> response = repository.findByAccountIdAndType(savedAccountId, TypeEnum.SMS);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TypeEnum.SMS);
	}

	@Test
	public void testSumByAccount() {
		Optional<Account> w = accountRepository.findById(savedAccountId);
		
		repository.save(new AccountItem(null, w.get(), DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80)));
		
		BigDecimal response = repository.sumByAccountId(savedAccountId);
		
		assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
	}
}
