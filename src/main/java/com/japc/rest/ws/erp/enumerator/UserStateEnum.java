package com.japc.rest.ws.erp.enumerator;

public enum UserStateEnum {

	ENABLED("A"), DISABLED("I"), LOCKED("L");

	private String state;

	private UserStateEnum(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
