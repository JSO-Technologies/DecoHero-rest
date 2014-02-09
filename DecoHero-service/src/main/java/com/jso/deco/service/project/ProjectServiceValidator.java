package com.jso.deco.service.project;

import org.apache.commons.lang.StringUtils;

import com.jso.deco.api.common.Category;
import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;

public class ProjectServiceValidator {

	public void validate(ProjectCreationRequest request) throws DHServiceException {
		if(request.getCategory() == null) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "category");
		}
		else if(request.getCategory() == Category.RM && request.getRoom() == null) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "room");
		}
		else if(StringUtils.isBlank(request.getTitle())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "title");
		}
		else if(StringUtils.isBlank(request.getDescription())) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "description");
		}
		else if(request.getImages() == null || request.getImages().isEmpty()) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "images");
		}
	}

	public void validate(String projectId) throws DHServiceException {
		if(StringUtils.isBlank(projectId)) {
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "projectId");
		}
	}

}
