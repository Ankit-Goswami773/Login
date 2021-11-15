package com.loginproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
	private long empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String designation;
	private String department;
	private double basicSaloary;
	private String status;

}
