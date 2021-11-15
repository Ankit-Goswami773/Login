package com.loginproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loginproject.common.CommonResponse;
import com.loginproject.service.OtpService;

@RestController
@CrossOrigin(origins = "http://localhost")
public class OtpController {
	@Autowired
	OtpService otpService;

	@PostMapping("/genrate-otp")
	public ResponseEntity<CommonResponse> genateOtp(@RequestParam("email") String email,@RequestHeader("Authorization") String token) {
	
		return new ResponseEntity<>(otpService.generatAndSave(email,token),HttpStatus.OK) ;
	}

	@GetMapping("/validate")
	public ResponseEntity<CommonResponse> validate(@RequestParam("otp") int otp, @RequestParam("email") String email,@RequestHeader("Authorization") String token) {
		
		return new ResponseEntity<>(otpService.validateOtp(otp, email,token),HttpStatus.OK) ;
	}
}
