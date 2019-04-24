package com.japc.rest.ws.erp.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the ROLE_MODULE database table.
 * 
 */
@Entity
@Table(name = "ROLE_MODULE")
public class RoleModule implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RoleModulePK id;

	public RoleModule() {
	}

	public RoleModulePK getId() {
		return this.id;
	}

	public void setId(RoleModulePK id) {
		this.id = id;
	}

}