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

import com.vivo.dto.UserAccountDTO;
import com.vivo.entity.User;
import com.vivo.entity.UserAccount;
import com.vivo.entity.Account;
import com.vivo.response.Response;
import com.vivo.service.UserAccountService;

@RestController
@RequestMapping(path = "user-account")
public class UserAccountController {
	
	@Autowired
	private UserAccountService service;
	
	@PostMapping
	public ResponseEntity<Response<UserAccountDTO>> create(@Valid @RequestBody UserAccountDTO dto, BindingResult result) {
		
		Response<UserAccountDTO> response = new Response<UserAccountDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}
		
		UserAccount uw = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(uw));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	public UserAccount convertDtoToEntity(UserAccountDTO dto) {
		UserAccount uw = new UserAccount();
		User u = new User();
		u.setId(dto.getUsers());
		Account w = new Account();
		w.setId(dto.getAccount());
		
		uw.setId(dto.getId());
		uw.setUsers(u);
		uw.setAccount(w);
		
		return uw;
	}
	
	public UserAccountDTO convertEntityToDto(UserAccount uw) {
		UserAccountDTO dto = new UserAccountDTO();
		dto.setId(uw.getId());
		dto.setUsers(uw.getUsers().getId());
		dto.setAccount(uw.getAccount().getId());
		
		return dto;
	}

}
