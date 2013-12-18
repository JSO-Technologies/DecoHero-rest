package com.jso.deco.service.adapter;

import org.springframework.http.HttpStatus;

import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.response.ErrorMessageResponse;
import com.jso.deco.api.service.response.ServiceResponse;

public class ServiceResponseAdapter {

	/**
	 * Generate response status and content from DH exception
	 * @param e
	 * @return
	 */
	public ServiceResponse fromException(DHServiceException e) {
		int status;
		switch(e.getDhMessage()) {
			case USER_ALREADY_EXISTS: 
				status = HttpStatus.CONFLICT.value();
				break;
			case USER_DOESNT_EXIST: 
				status = HttpStatus.NOT_FOUND.value();
				break;
			case MISSING_FIELD: 
				status = HttpStatus.BAD_REQUEST.value();
				break;
			default : 
				status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
		
		ErrorMessageResponse errorMessage = new ErrorMessageResponse(e.getDhMessage().name(), e.getDetails());

		return new ServiceResponse(status, errorMessage);
	}

}
