package com.japc.rest.ws.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.User;

@Repository
@Transactional
public interface UserJpaRepository extends JpaRepository<User, String> {
	
	//@Query("SELECT DISTINCT u FROM User u WHERE  u.userEmail=(:email)")
	public User findByUserEmail(String userEmail);
}
