package com.jso.deco.api.service.response;

import javax.ws.rs.core.Response.Status;

public class ServiceResponse {
	private final Status status;
	private final Object content;
	
	public ServiceResponse(Status status, Object content) {
		this.status = status;
		this.content = content;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Object getContent() {
		return content;
	}
	
}
