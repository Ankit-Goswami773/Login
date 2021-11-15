package com.loginproject.jwt_config.jwt_model_clasess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtRequest {

	private String userEmail;
	private String password;

}
