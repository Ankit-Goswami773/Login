package com.loginproject.jwt_config.Login_security_config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.loginproject.applications_exceptions.CommonLoginException;
import com.loginproject.jwt_config.jwt_service.CustomLoginService;
import com.loginproject.jwtconfig.jwthelper.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CustomLoginService customLoginService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requstTokenheader = request.getHeader("Authorization");
		String username = null;

		if (requstTokenheader != null && requstTokenheader.startsWith("Bearer ")) {
			String jwtToken = requstTokenheader.substring(7);
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (Exception e) {

				e.printStackTrace();
			}
			UserDetails userDetails = this.customLoginService.loadUserByUsername(username);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, userDetails.getAuthorities(), null);

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			} else {
				throw new CommonLoginException("not able to validate", 400, HttpStatus.BAD_REQUEST);
			}
		}
		filterChain.doFilter(request, response);
	}

}
