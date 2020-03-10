package com.vivo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AccountDTO {

	private Long id;
	@Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres")
	@NotNull(message = "O nome não pode ser nulo")
	private String name;
	@NotNull(message = "O valor não pode ser nulo")
	private BigDecimal value;
	@NotNull(message = "O valor não pode ser nulo")
	private Number lineNumber;
}
