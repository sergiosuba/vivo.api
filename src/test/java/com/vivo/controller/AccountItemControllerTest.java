package com.vivo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivo.dto.AccountItemDTO;
import com.vivo.entity.User;
import com.vivo.entity.UserAccount;
import com.vivo.entity.Account;
import com.vivo.entity.AccountItem;
import com.vivo.service.UserService;
import com.vivo.service.UserAccountService;
import com.vivo.service.AccountItemService;
import com.vivo.util.enums.TypeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountItemControllerTest {
	
	@MockBean
	AccountItemService service;
	@MockBean
	UserAccountService userAccountService;
	@MockBean
	UserService userService;
	
	@Autowired
	MockMvc mvc;
	
	private static final Long ID = 1L;
	private static final Date DATE = new Date();
	private static final LocalDate TODAY = LocalDate.now();
	private static final TypeEnum TYPE = TypeEnum.DAT;
	private static final String DESCRIPTION = "Conta de Luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	private static final String URL = "/account-item";
	
	@Test
	@WithMockUser
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(AccountItem.class))).willReturn(getMockAccountItem());
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.date").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.description").value(DESCRIPTION))
		.andExpect(jsonPath("$.data.type").value(TYPE.getValue()))
		.andExpect(jsonPath("$.data.value").value(VALUE))
		.andExpect(jsonPath("$.data.account").value(ID));
		
	}
	
	@Test
	@WithMockUser
	public void testFindBetweenDates() throws Exception {
		List<AccountItem> list = new ArrayList<>();
		list.add(getMockAccountItem());
		Page<AccountItem> page = new PageImpl(list);
		
		String startDate = TODAY.format(getDateFormater());
		String endDate = TODAY.plusDays(5).format(getDateFormater());
		
		User user = new User();
		user.setId(1L);
		
		BDDMockito.given(service.findBetweenDates(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt())).willReturn(page);
		BDDMockito.given(userService.findByEmail(Mockito.anyString())).willReturn(Optional.of(user));
		BDDMockito.given(userAccountService.findByUsersIdAndAccountId(Mockito.anyLong(), Mockito.anyLong())).willReturn(Optional.of(new UserAccount()));
		
		mvc.perform(MockMvcRequestBuilders.get(URL + "/1?startDate=" + startDate + "&endDate=" + endDate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.content[0].id").value(ID))
		.andExpect(jsonPath("$.data.content[0].date").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.content[0].description").value(DESCRIPTION))
		.andExpect(jsonPath("$.data.content[0].type").value(TYPE.getValue()))
		.andExpect(jsonPath("$.data.content[0].value").value(VALUE))
		.andExpect(jsonPath("$.data.content[0].account").value(ID));
		
	}
	
	@Test
	@WithMockUser
	public void testFindByType() throws Exception {
		List<AccountItem> list = new ArrayList<>();
		list.add(getMockAccountItem());
		
		BDDMockito.given(service.findByAccountAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(list);
		
		mvc.perform(MockMvcRequestBuilders.get(URL+"/type/1?type=DATA")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.[0].id").value(ID))
		.andExpect(jsonPath("$.data.[0].date").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.[0].description").value(DESCRIPTION))
		.andExpect(jsonPath("$.data.[0].type").value(TYPE.getValue()))
		.andExpect(jsonPath("$.data.[0].value").value(VALUE))
		.andExpect(jsonPath("$.data.[0].account").value(ID));

	}
	
	@Test
	@WithMockUser
	public void testSumByAccount() throws Exception {
		BigDecimal value = BigDecimal.valueOf(536.90);
		
		BDDMockito.given(service.sumByAccountId(Mockito.anyLong())).willReturn(value);
		
		mvc.perform(MockMvcRequestBuilders.get(URL+"/total/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value("536.9"));

	}
	
	@Test
	@WithMockUser
	public void testUpdate() throws Exception {
		
		String description = "Nova descrição";
		Account w = new Account();
		w.setId(ID);
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(getMockAccountItem()));
		BDDMockito.given(service.save(Mockito.any(AccountItem.class))).willReturn(new AccountItem(1L, w, DATE, TypeEnum.SMS, description, VALUE));
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.date").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.description").value(description))
		.andExpect(jsonPath("$.data.type").value(TypeEnum.SMS.getValue()))
		.andExpect(jsonPath("$.data.value").value(VALUE))
		.andExpect(jsonPath("$.data.account").value(ID));
		
	}
	
	@Test
	@WithMockUser
	public void testUpdateAccountChange() throws Exception {
		
		Account w = new Account();
		w.setId(99L);
		
		AccountItem wi = new AccountItem(1L, w, DATE, TypeEnum.SMS, DESCRIPTION, VALUE);
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(wi));
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("Você não pode alterar a carteira"));
		
	}
	
	@Test
	@WithMockUser
	public void testUpdateInvalidId() throws Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("AccountItem não encontrado"));
		
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testDelete() throws JsonProcessingException, Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(new AccountItem()));
		
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value("AccountItem de id "+ ID +" apagada com sucesso"));
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testDeleteInvalid() throws Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/99")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("AccountItem de id "+ 99 + " não encontrada"));
		
	}
	
	private AccountItem getMockAccountItem() {
		Account w = new Account();
		w.setId(1L);
		
		AccountItem wi = new AccountItem(1L, w, DATE, TYPE, DESCRIPTION, VALUE);
		return wi;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		AccountItemDTO dto = new AccountItemDTO();
		dto.setId(ID);
		dto.setDate(DATE);
		dto.setDescription(DESCRIPTION);
		dto.setType(TYPE.getValue());
		dto.setValue(VALUE);
		dto.setAccount(ID);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
	private DateTimeFormatter getDateFormater() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return formatter;
	}

}
