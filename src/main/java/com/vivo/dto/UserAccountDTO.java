package com.vivo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserAccountDTO {

	private Long id;
	@NotNull(message = "Informe o id do usuário")
	private Long users;
	@NotNull(message = "Informe o id da conta")
	private Long account;
}
