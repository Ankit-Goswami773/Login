package com.loginproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loginproject.common.CommonResponse;
import com.loginproject.jwtconfig.jwthelper.JwtUtil;
import com.loginproject.models.LoginDTO;
import com.loginproject.service.LoginService;

@RestController
@CrossOrigin(origins = "http://localhost")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public Object loginCheck(@RequestBody  LoginDTO loginDto) {
		return loginService.loginCheck(loginDto);
	}

	@GetMapping("/validate/{captcha}")
	public ResponseEntity<CommonResponse> validCaptcha(@PathVariable("captcha") String captcha) {
		return new ResponseEntity<>(loginService.validateCaptcha(captcha), HttpStatus.OK);
	}

	@PostMapping("/saveLoginCredentials")
	public ResponseEntity<CommonResponse> saveData(@RequestBody  LoginDTO loginDto) {
		return new ResponseEntity<>(loginService.saveData(loginDto), HttpStatus.CREATED);
	}

	@GetMapping("/validateToken")
	public ResponseEntity<Boolean> validTokens(@RequestParam("token") String token) {
		return new ResponseEntity<>(jwtUtil.validateToken(token), HttpStatus.OK);
	}

}
