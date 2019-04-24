package com.japc.rest.ws.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.Password;

@Repository
@Transactional
public interface PasswordJpaRepository extends JpaRepository<Password, Long> {

	
	@Query("SELECT DISTINCT p FROM Password p WHERE  p.user.userId=(:username) AND p.passwordState = 1")
	public Password findCurrentByUsername(@Param("username")String username);
	
	@Query("SELECT DISTINCT p FROM Password p WHERE  p.user.userId=(:username)")
	public List<Password> findAllByUsername(@Param("username")String username);
	
	
}
