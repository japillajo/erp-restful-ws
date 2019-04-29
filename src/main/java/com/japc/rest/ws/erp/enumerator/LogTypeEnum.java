package com.japc.rest.ws.erp.enumerator;

public enum LogTypeEnum {

	INFO("INFO"), WARNING("WARN"), SEVERE("ERROR");

	private String type;

	private LogTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
