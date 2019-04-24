package com.japc.rest.ws.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japc.rest.ws.erp.model.Role;

@Repository
@Transactional
public interface RoleJpaRepository extends JpaRepository<Role, Integer> {
	
	@Query("SELECT DISTINCT r FROM Role r INNER JOIN User u ON r.roleId = u.role.roleId WHERE u.userId=(:userId)")
	public Role findByUsername(@Param("userId")String userId);
}
