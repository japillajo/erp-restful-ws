package com.japc.rest.ws.erp.dto;

public class Response {
	
	public Header header;
	public Detail detail;

	protected Response() {
	}

	public Response(Header header, Detail detail) {
		super();
		this.header = header;
		this.detail = detail;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	
}
