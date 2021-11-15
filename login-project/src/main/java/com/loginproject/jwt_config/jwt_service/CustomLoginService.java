package com.loginproject.jwt_config.jwt_service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loginproject.entity.Login;
import com.loginproject.repository.LoginRepository;

@Service
public class CustomLoginService implements UserDetailsService {
	@Autowired
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Login findByUserEmail = loginRepository.findByUserEmail(username);
		if (findByUserEmail == null) {
			throw new UsernameNotFoundException(" not found in database of Employee Email :" + username);
		}

		return new User(findByUserEmail.getUserEmail(), findByUserEmail.getPassword(), new ArrayList<>());

	}

}
