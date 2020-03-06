package com.vivo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;
	@NotNull
	@Email(message="E-mail inválido")
	private String email;
	@NotNull
	@Length(min=3, max=30, message="O nome deve conter entre 3 e 50 caracteres")
	private String name;
	@NotNull
	@Length(min=6, message="A senha deve conter no mínimo 6 caracteres")
	private String password;
}
