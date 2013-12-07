package com.jso.deco.service.user;

import org.apache.commons.lang.StringUtils;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserResgisterRequest;

public class UserServiceValidator {
	
	public void validateCreationValues(UserResgisterRequest request) throws DHServiceException {
		if(StringUtils.isBlank(request.getEmail())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "email");
		}
		else if(StringUtils.isBlank(request.getUsername())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "username");
		}
		else if(StringUtils.isBlank(request.getFirstName())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "firstname");
		}
		else if(StringUtils.isBlank(request.getLastName())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "lastname");
		}
		else if(StringUtils.isBlank(request.getPassword())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "password");
		}
		else if(request.getBirthDate() == null) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "birthdate");
		}
	}
}
