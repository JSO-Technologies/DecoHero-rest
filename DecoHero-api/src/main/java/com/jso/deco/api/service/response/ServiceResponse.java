package com.jso.deco.api.service.response;

public class ServiceResponse {
	private final int status;
	private final Object content;
	
	public ServiceResponse(int status, Object content) {
		this.status = status;
		this.content = content;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Object getContent() {
		return content;
	}
	
}
