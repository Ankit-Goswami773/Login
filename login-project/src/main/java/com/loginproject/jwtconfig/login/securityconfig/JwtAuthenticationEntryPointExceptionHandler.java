package com.loginproject.jwtconfig.login.securityconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPointExceptionHandler implements AuthenticationEntryPoint {

	
	/** this class is us for custom error message and status code when Unauthorized user send request */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.sendError(401, "Unauthorized");
		
	}

	
	
}
