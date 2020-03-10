package com.vivo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users_account")
@Data
public class UserAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8104860055294069590L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JoinColumn(name = "users", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User users;
	@JoinColumn(name = "account", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

}
