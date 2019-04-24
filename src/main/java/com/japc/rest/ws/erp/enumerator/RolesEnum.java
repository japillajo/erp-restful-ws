package com.japc.rest.ws.erp.enumerator;

public enum RolesEnum {

	ADMINISTRATOR(1), EMPLOYEE(2);

	private int roleCode;

	private RolesEnum(int roleCode) {
		this.roleCode = roleCode;
	}

	public int getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}

}
