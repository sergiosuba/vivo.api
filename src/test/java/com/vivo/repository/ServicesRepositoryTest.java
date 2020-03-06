package com.vivo.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Service;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ServicesRepositoryTest {

	@Autowired
	public ServiceRepository repository;
	
	@Test
	public void testSave() {
		Service s = new Service();
		
		s.setPlanId(1L);
		s.setServiceName("sms");
		s.setValue(1L);
		s.setCreationDate("01-02-2020");
		
		Service response = repository.save(s);
		
		assertNotNull(response);
	}
}
