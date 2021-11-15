package com.loginproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loginId;
	@Column(unique = true)
	private String userEmail;
	private String password;
	private String roles;
	private long empId;

}
