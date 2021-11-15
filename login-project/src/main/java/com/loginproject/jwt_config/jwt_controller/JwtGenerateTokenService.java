package com.loginproject.jwt_config.jwt_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.loginproject.applications_exceptions.CommonLoginException;
import com.loginproject.jwt_config.jwt_model_clasess.JwtRequest;
import com.loginproject.jwtconfig.jwthelper.JwtUtil;
import com.loginproject.jwtconfig.jwtservice.CustomLoginService;

@Service
public class JwtGenerateTokenService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomLoginService customLoginService;

	@Autowired
	private JwtUtil jwtUtil;

	public String generateToken(@RequestBody JwtRequest jwtRequest) {
	
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserEmail(), jwtRequest.getPassword()));

		}  catch (Exception e) {
			e.printStackTrace();
			throw new CommonLoginException("Unauthorized", 401, HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = this.customLoginService.loadUserByUsername(jwtRequest.getUserEmail());
		return this.jwtUtil.generateToken(userDetails);

	}

}
