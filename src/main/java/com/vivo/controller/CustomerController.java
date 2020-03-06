package com.vivo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivo.dto.CustomerDTO;
import com.vivo.entity.Customer;
import com.vivo.response.Response;
import com.vivo.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@PostMapping
	public ResponseEntity<Response<CustomerDTO>> create(@Valid @RequestBody CustomerDTO dto, BindingResult result){
		
		Response<CustomerDTO> response = new Response<CustomerDTO>();
		
		Customer customer = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(customer));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private Customer convertDtoToEntity(CustomerDTO dto) {
		Customer c = new Customer();
		c.setName(dto.getName());
		c.setEmail(dto.getEmail());
		c.setPassword(dto.getPassword());
		
		return c;
	}
	
	private CustomerDTO convertEntityToDto(Customer c) {
		CustomerDTO dto = new CustomerDTO();
		dto.setName(c.getName());
		dto.setEmail(c.getEmail());
		dto.setPassword(c.getPassword());
		
		return dto;
	}
}
