package com.japc.rest.ws.erp.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class Header {
	
	public String uuid;
	public Date timestamp;
	public Integer status;
	public String error;
	public String path;
	
	public Header(String uuid, HttpStatus httpStatus, String path) {
		super();
		this.uuid = uuid;
		this.timestamp = new Date();
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.path = path;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
