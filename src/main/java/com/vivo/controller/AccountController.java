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

import com.vivo.dto.AccountDTO;
import com.vivo.entity.Account;
import com.vivo.response.Response;
import com.vivo.service.AccountService;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@PostMapping
	public ResponseEntity<Response<AccountDTO>> create(@Valid @RequestBody AccountDTO dto, BindingResult result) {
		
		Response<AccountDTO> response = new Response<AccountDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}
		
		Account w = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(w));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	private Account convertDtoToEntity(AccountDTO dto) {
		Account a = new Account();
		a.setId(dto.getId());
		a.setName(dto.getName());
		a.setValue(dto.getValue());
		
		return a;
	}
	
	private AccountDTO convertEntityToDto(Account a) {
		AccountDTO dto = new AccountDTO();
		dto.setId(a.getId());
		dto.setName(a.getName());
		dto.setValue(a.getValue());

		return dto;
	}

}
