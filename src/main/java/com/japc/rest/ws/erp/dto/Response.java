package com.japc.rest.ws.erp.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class Response {
	
	public Date timestamp;
	public Integer status;
	public String error;
	public String code;
	public String message;
	public String trace;
	public String path;
	public Object data;
	
	protected Response() {
	}

	public Response(HttpStatus httpStatus, String code, String message, String trace, String path) {
		super();
		this.timestamp = new Date();
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.code = code;
		this.message = message;
		this.trace = trace;
		this.path = path;
	}

	public Response(HttpStatus httpStatus, String code, String message, String path, Object data) {
		super();
		this.timestamp = new Date();
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.code = code;
		this.message = message;
		this.path = path;
		this.data = data;
	}
	
	public Response(HttpStatus httpStatus, String code, String message, String path) {
		super();
		this.timestamp = new Date();
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		this.code = code;
		this.message = message;
		this.path = path;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	
	}
	
}
