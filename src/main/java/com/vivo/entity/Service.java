package com.vivo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Service  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2965649290345128990L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false)
	private Long planId;
	@Column(nullable = false)
	private String ServiceName;
	@Column(nullable = false)
	private Long value;
	@Column(nullable = false)
	private String creationDate;
}
