package com.loginproject.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpDTO {
	private long id;
	private int otpNumber;
	private long employeeId;
	private Timestamp timestamp;
}
