package com.vivo.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Customer;
import com.vivo.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

	@MockBean
	CustomerRepository repository;
	
	@Autowired
	CustomerService service;
	@Before
	public void setUp() {
		BDDMockito.given(repository.findByEmailEquals(Mockito.anyString())).willReturn(Optional.of(new Customer()));
	}
	
	@Test
	public void testFindByEmail() {
		Optional<Customer> customer = service.findByEmail("email@test.com");
		
		assertTrue(customer.isPresent());
	}
}
