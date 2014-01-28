package com.jso.deco.service.user;

import org.apache.commons.lang.StringUtils;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserInfosRequest;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;

public class UserServiceValidator {
	
	private static final String IMAGE_DATA_URL_TYPE = "data:image/png;base64,";
	private static final String IMAGE_DATA_URL = "data:image/";

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

	/**
	 * Validate user update infos
	 * 
	 * @param request
	 * @throws DHServiceException 
	 */
	public void validate(UserInfosRequest request) throws DHServiceException {
		if(request.getBirthdateTimestamp() == null) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "birthdate");
		}
	}

	/**
	 * Validate png image data url
	 * @param avatarDataUrl
	 * @throws DHServiceException 
	 */
	public void validatePngImageDataUrl(String avatarDataUrl) throws DHServiceException {
		if(! avatarDataUrl.startsWith(IMAGE_DATA_URL_TYPE)) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "Param is not a png image data url");
		}
	}
	
	/**
	 * Validate image data url
	 * @param avatarDataUrl
	 * @throws DHServiceException 
	 */
	public void validateImageDataUrl(String dataUrl) throws DHServiceException {
		if(! dataUrl.startsWith(IMAGE_DATA_URL)) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "Param is not an image data url");
		}
	}
}
