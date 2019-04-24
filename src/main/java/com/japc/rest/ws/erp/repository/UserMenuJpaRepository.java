package com.japc.rest.ws.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.Menu;
import com.japc.rest.ws.erp.model.UserMenu;

@Repository
@Transactional
public interface UserMenuJpaRepository extends JpaRepository<Menu, Integer> {
	
	@Query("SELECT DISTINCT um FROM UserMenu um WHERE um.id.userId = (:username)")
	public List<UserMenu> findAllByUsername(@Param("username")String username);
	
}
