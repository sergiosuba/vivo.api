package com.vivo.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vivo.entity.Plan;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanRepositoryTest {

	@Autowired
	PlanRepository repository;
	
	@Test
	public void testSave() {
		Plan p = new Plan();
		p.setCustomerId(1);
		p.setPlanName("Plano Plus Total 2000");
		p.setNumber(988880000);
		
		Plan response = repository.save(p);
		
		assertNotNull(response);
	}
}
