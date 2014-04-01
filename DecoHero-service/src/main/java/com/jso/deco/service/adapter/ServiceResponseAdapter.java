package com.jso.deco.service.adapter;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.ws.rs.core.Response.Status;

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
		Status status;
		switch(e.getDhMessage()) {
			case USER_ALREADY_EXISTS: 
				status = CONFLICT;
				break;
			case USER_DOESNT_EXIST: 
			case PROJECT_DOESNT_EXIST: 
				status = NOT_FOUND;
				break;
			case MISSING_FIELD: 
				status = BAD_REQUEST;
				break;
			default : 
				status = INTERNAL_SERVER_ERROR;
		}
		
		ErrorMessageResponse errorMessage = new ErrorMessageResponse(e.getDhMessage().name(), e.getDetails());

		return new ServiceResponse(status, errorMessage);
	}

}
