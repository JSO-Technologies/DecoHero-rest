package com.jso.deco.api.exception;

public enum DHMessageCode {
	/**
	 * User creation : username or email already exists
	 */
	USER_ALREADY_EXISTS,
	/**
	 * Login : user does not exist
	 */
	USER_DOESNT_EXIST,
	/**
	 * Generic : missing parameter in request
	 */
	MISSING_FIELD,
	/**
	 * Internal error
	 */
	INTERNAL_ERROR
}
