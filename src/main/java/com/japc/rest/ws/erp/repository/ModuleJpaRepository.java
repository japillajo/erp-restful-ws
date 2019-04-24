package com.japc.rest.ws.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.Module;

@Repository
@Transactional
public interface ModuleJpaRepository extends JpaRepository<Module, Integer> {
	
	@Query("SELECT DISTINCT m FROM Module m INNER JOIN RoleModule rm ON m.moduleId = rm.id.moduleId WHERE rm.id.roleId=(:roleId)")
	public List<Module> findAllByRoleId(@Param("roleId")Integer roleId);
	
}
