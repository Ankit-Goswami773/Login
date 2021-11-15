package com.loginproject.service;

import com.loginproject.common.CommonResponse;
import com.loginproject.models.LoginDTO;

public interface LoginService {

	
	public CommonResponse loginCheck(LoginDTO loginDTO);
	
	public CommonResponse validateCaptcha(String captcha);

	public CommonResponse saveData(LoginDTO loginDTO);
}
