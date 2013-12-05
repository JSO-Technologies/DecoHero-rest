package com.jso.deco.service.user;

import org.springframework.http.HttpStatus;

import com.jso.deco.api.service.User;
import com.jso.deco.controller.UserController;
import com.jso.deco.service.exception.DHMessage;
import com.jso.deco.service.exception.DHServiceException;

public class UserValidator {
	private UserController controller;
	
	public void validateCreationValues(User user) throws DHServiceException {
		if(controller.usernameExists(user)) {
			throw new DHServiceException(HttpStatus.CONFLICT, DHMessage.USERNAME_ALREADY_EXISTS);
		}
	}

	public void setController(UserController controller) {
		this.controller = controller;
	}
}
