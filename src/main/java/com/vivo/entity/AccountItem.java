package com.vivo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vivo.util.enums.TypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1796790346218894235L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JoinColumn(name = "account", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
	@NotNull
	private Date date;
	@NotNull
	@Enumerated(EnumType.STRING)
	private TypeEnum type;
	@NotNull
	private String description;
	@NotNull
	private BigDecimal value;
}
