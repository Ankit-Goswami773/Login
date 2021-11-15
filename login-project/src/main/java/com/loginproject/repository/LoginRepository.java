package com.loginproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginproject.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login,Integer> {

    public Login findByUserEmailAndPassword(String email, String password);

	public Login findByUserEmail(String email);
	
}
