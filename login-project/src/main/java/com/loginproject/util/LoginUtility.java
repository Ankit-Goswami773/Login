package com.loginproject.util;

import org.springframework.beans.BeanUtils;

import com.loginproject.entity.Login;
import com.loginproject.entity.Otp;
import com.loginproject.models.LoginDTO;
import com.loginproject.models.OtpDTO;

public final class LoginUtility {
	private LoginUtility() {

	}

	public static Login loginDtoToEntity(LoginDTO loginDTO) {
		Login login = new Login();
		BeanUtils.copyProperties(loginDTO, login);
		return login;
	}

	public static Otp otpDtoToEntity(OtpDTO otpDTO) {
		Otp otp = new Otp();
		BeanUtils.copyProperties(otpDTO, otp);
		return otp;
	}
}
