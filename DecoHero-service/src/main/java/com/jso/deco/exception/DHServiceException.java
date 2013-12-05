package com.jso.deco.exception;

import org.springframework.http.HttpStatus;

public class DHServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;
	private final DHMessage dhMessage;
	
	public DHServiceException(final HttpStatus httpStatus, final DHMessage dhMessage) {
		this.httpStatus = httpStatus;
		this.dhMessage = dhMessage;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public DHMessage getDhMessage() {
		return dhMessage;
	}
}
