package com.vivo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Plan implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6630296843399761789L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false)
	private long customerId;
	@Column(nullable = false)
	private String planName;
	@Column(nullable = false)
	private long number;
}
