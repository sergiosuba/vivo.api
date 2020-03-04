package com.vivo.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

	private static final String EMAIL = "email@email.com";
	@Autowired
	CustomerRepository repository;
	
	@Before
	public void setUp() {
		Customer c = new Customer();
		c.setName("Nome de Teste");
		c.setEmail(EMAIL);
		c.setPassword("12345678");
		
		repository.save(c);
	}
	
	@After
	public void tearDown() {
		
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		Customer c = new Customer();
		c.setName("Nome de Teste");
		c.setEmail("email@email.com");
		c.setPassword("12345678");
		
		Customer response = repository.save(c);
		
		assertNotNull(response);
	}
	
	public void testFindByEmail() {
		Optional<Customer> response = repository.findByEmailEquals(EMAIL);
	}
	
}
