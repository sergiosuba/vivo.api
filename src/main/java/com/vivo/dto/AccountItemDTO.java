package com.vivo.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AccountItemDTO {

	private Long id;
	@NotNull(message = "Insira o id da conta")
	private Long account;
	@NotNull(message = "Informe uma data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date date;
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp="^(DATA|SMS|MINUTES)$", message = "Para o tipo somente são aceitos os valores DATA, SMS ou MINUTES")
	private String type;
	@NotNull(message = "Informe uma descrição")
	@Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
	private String description;
	@NotNull(message = "Informe um valor")
	private BigDecimal value;
}
