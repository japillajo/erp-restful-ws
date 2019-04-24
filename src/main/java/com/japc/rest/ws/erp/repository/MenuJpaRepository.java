package com.japc.rest.ws.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.Menu;

@Repository
@Transactional
public interface MenuJpaRepository extends JpaRepository<Menu, Integer> {
	
	@Query("SELECT DISTINCT m FROM Menu m  WHERE m.module.moduleId=(:moduleId) AND m.menuState= true")
	public List<Menu> findAllByModuleId(@Param("moduleId")Integer moduleId);
}
