package com.jso.deco.service.user;

import org.apache.commons.lang.StringUtils;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;

public class UserServiceValidator {
	
	/**
	 * Validate registration infos
	 * 
	 * @param request
	 * @throws DHServiceException
	 */
	public void validate(UserRegisterRequest request) throws DHServiceException {
		if(StringUtils.isBlank(request.getEmail())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "email");
		}
		else if(StringUtils.isBlank(request.getUsername())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "username");
		}
		else if(StringUtils.isBlank(request.getFirstname())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "firstname");
		}
		else if(StringUtils.isBlank(request.getLastname())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "lastname");
		}
		else if(StringUtils.isBlank(request.getPassword())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "password");
		}
		else if(request.getBirthdateTimestamp() == null) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "birthdate");
		}
	}

	/**
	 * Validate authentication infos
	 * 
	 * @param request
	 * @throws DHServiceException 
	 */
	public void validate(UserLoginRequest request) throws DHServiceException {
		if(StringUtils.isBlank(request.getEmail())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "email");
		}
		else if(StringUtils.isBlank(request.getPassword())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "password");
		}
	}
}
