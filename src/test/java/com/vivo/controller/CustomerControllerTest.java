package com.vivo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivo.dto.CustomerDTO;
import com.vivo.entity.Customer;
import com.vivo.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTest {

	private static final String EMAIL = "email@mail.com";
	private static final String NAME = "Test Name";
	private static final String PASSWORD = "123456";
	private static final String URL = "/customer";
	
	@MockBean
	CustomerService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Customer.class))).willReturn(getMockCustomer());
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	public Customer getMockCustomer() {
		Customer c = new Customer();
		c.setEmail(EMAIL);
		c.setName(NAME);
		c.setPassword(PASSWORD);
		
		return c;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		
		CustomerDTO dto = new CustomerDTO();
		dto.setEmail(EMAIL);
		dto.setName(NAME);
		dto.setPassword(PASSWORD);
		
		ObjectMapper mapper = new ObjectMapper();
		
		return  mapper.writeValueAsString(dto);
		
	}
}
