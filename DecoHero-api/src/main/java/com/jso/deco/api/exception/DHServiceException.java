package com.jso.deco.api.exception;


public class DHServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	private final DHMessageCode dhMessage;
	private final String details;
	
	public DHServiceException(final DHMessageCode dhMessage, final String details) {
		this.dhMessage = dhMessage;
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
	
	public DHMessageCode getDhMessage() {
		return dhMessage;
	}
}
