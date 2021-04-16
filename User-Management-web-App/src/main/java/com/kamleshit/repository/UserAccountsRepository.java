package com.kamleshit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamleshit.entity.UserAccountEntity;

public interface UserAccountsRepository extends JpaRepository<UserAccountEntity,Integer> {

	public UserAccountEntity findByUserEmailAndUserPazzword(String email,String pwd);
	
	public UserAccountEntity findByUserEmail(String Email);
	
}
