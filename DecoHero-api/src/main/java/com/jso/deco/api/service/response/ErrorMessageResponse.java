package com.jso.deco.api.service.response;

public class ErrorMessageResponse {
	private final String error;
	private final String details;
	
	public ErrorMessageResponse(final String error, final String details) {
		this.error = error;
		this.details = details;
	}
	
	public String getError() {
		return error;
	}
	
	public String getDetails() {
		return details;
	}
}
