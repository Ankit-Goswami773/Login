package com.loginproject.service;

import com.loginproject.common.CommonResponse;

public interface OtpService {
	public CommonResponse generatAndSave(String email, String token);
	public CommonResponse validateOtp(int i,String email, String token);
}
