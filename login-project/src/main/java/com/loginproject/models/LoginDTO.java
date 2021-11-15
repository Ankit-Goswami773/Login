package com.loginproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO {
	private int loginId;
	private String userEmail;
	private String password;
	private String roles;
	private long empId;
}
