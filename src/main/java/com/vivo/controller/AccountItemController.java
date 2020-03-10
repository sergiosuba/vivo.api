package com.vivo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivo.dto.AccountItemDTO;
import com.vivo.entity.UserAccount;
import com.vivo.entity.Account;
import com.vivo.entity.AccountItem;
import com.vivo.response.Response;
import com.vivo.service.UserAccountService;
import com.vivo.service.AccountItemService;
import com.vivo.util.Util;
import com.vivo.util.enums.TypeEnum;

@RestController
@RequestMapping("account-item")
public class AccountItemController {

	@Autowired
	private AccountItemService service;
	@Autowired
	private UserAccountService userAccountService;
	
	private static final Logger log = LoggerFactory.getLogger(AccountItemController.class);

	@PostMapping
	public ResponseEntity<Response<AccountItemDTO>> create(@Valid @RequestBody AccountItemDTO dto, BindingResult result) {

		Response<AccountItemDTO> response = new Response<AccountItemDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		AccountItem wi = service.save(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(wi));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping(value = "/{account}")
	public ResponseEntity<Response<Page<AccountItemDTO>>> findBetweenDates(@PathVariable("account") Long account,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		
		Response<Page<AccountItemDTO>> response = new Response<Page<AccountItemDTO>>();
		
		
		Optional<UserAccount> uw = userAccountService.findByUsersIdAndAccountId(Util.getAuthenticatedUserId(), account);
		
		if (!uw.isPresent()) {
			response.getErrors().add("Você não tem acesso a essa carteira");
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<AccountItem> items = service.findBetweenDates(account, startDate, endDate, page);
		Page<AccountItemDTO> dto = items.map(i -> this.convertEntityToDto(i));
		response.setData(dto);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/type/{account}")
	public ResponseEntity<Response<List<AccountItemDTO>>> findByAccountIdAndType(@PathVariable("account") Long account,
			@RequestParam("type") String type) {
		
		log.info("Buscando por carteira {} e tipo {}", account, type);
		
		Response<List<AccountItemDTO>> response = new Response<List<AccountItemDTO>>();
		List<AccountItem> list = service.findByAccountAndType(account, TypeEnum.getEnum(type));

		List<AccountItemDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(this.convertEntityToDto(i)));
		response.setData(dto);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/total/{account}")
	public ResponseEntity<Response<BigDecimal>> sumByAccountId(@PathVariable("account") Long account) {

		Response<BigDecimal> response = new Response<BigDecimal>();
		BigDecimal value = service.sumByAccountId(account);
		response.setData(value == null ? BigDecimal.ZERO : value);

		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<Response<AccountItemDTO>> update(@Valid @RequestBody AccountItemDTO dto, BindingResult result) {

		Response<AccountItemDTO> response = new Response<AccountItemDTO>();

		Optional<AccountItem> wi = service.findById(dto.getId());

		if (!wi.isPresent()) {
			result.addError(new ObjectError("AccountItem", "AccountItem não encontrado"));
		} else if (wi.get().getAccount().getId().compareTo(dto.getAccount()) != 0) {
				result.addError(new ObjectError("AccountItemChanged", "Você não pode alterar a carteira"));
		}

		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		AccountItem saved = service.save(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(saved));
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(value = "/{accountItemId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("accountItemId") Long accountItemId) {
		Response<String> response = new Response<String>();

		Optional<AccountItem> ai = service.findById(accountItemId);

		if (!ai.isPresent()) {
			response.getErrors().add("AccountItem de id " + accountItemId + " não encontrada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		service.deleteById(accountItemId);
		response.setData("AccountItem de id "+ accountItemId + " apagada com sucesso");
		return ResponseEntity.ok().body(response);
	}

	private AccountItem convertDtoToEntity(AccountItemDTO dto) {
		AccountItem ai = new AccountItem();
		ai.setDate(dto.getDate());
		ai.setDescription(dto.getDescription());
		ai.setId(dto.getId());
		ai.setType(TypeEnum.getEnum(dto.getType()));
		ai.setValue(dto.getValue());

		Account a = new Account();
		a.setId(dto.getAccount());
		ai.setAccount(a);

		return ai;
	}

	private AccountItemDTO convertEntityToDto(AccountItem ai) {
		AccountItemDTO dto = new AccountItemDTO();
		dto.setDate(ai.getDate());
		dto.setDescription(ai.getDescription());
		dto.setId(ai.getId());
		dto.setType(ai.getType().getValue());
		dto.setValue(ai.getValue());
		dto.setAccount(ai.getAccount().getId());

		return dto;
	}
}
