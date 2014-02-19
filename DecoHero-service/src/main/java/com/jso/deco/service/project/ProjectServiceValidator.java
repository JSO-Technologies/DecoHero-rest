package com.jso.deco.service.project;

import static com.jso.deco.api.exception.DHMessageCode.MISSING_FIELD;

import org.apache.commons.lang.StringUtils;

import com.jso.deco.api.common.Category;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;

public class ProjectServiceValidator {

	private static final String DATE_FORMAT = "^[0-9]{14}$";

	public void validate(final ProjectCreationRequest request) throws DHServiceException {
		if(request.getCategory() == null) {
			throw new DHServiceException(MISSING_FIELD, "category");
		}
		else if(request.getCategory() == Category.RM && request.getRoom() == null) {
			throw new DHServiceException(MISSING_FIELD, "room");
		}
		else if(StringUtils.isBlank(request.getTitle())) {
			throw new DHServiceException(MISSING_FIELD, "title");
		}
		else if(StringUtils.isBlank(request.getDescription())) {
			throw new DHServiceException(MISSING_FIELD, "description");
		}
		else if(request.getImages() == null || request.getImages().isEmpty()) {
			throw new DHServiceException(MISSING_FIELD, "images");
		}
	}

	public void validate(final String projectId) throws DHServiceException {
		if(StringUtils.isBlank(projectId)) {
			throw new DHServiceException(MISSING_FIELD, "projectId");
		}
	}

	public void validate(final String userId, final String fromDate) throws DHServiceException {
		if(StringUtils.isBlank(userId)) {
			throw new DHServiceException(MISSING_FIELD, "userId");
		}
		if(fromDate != null && ! fromDate.matches(DATE_FORMAT)) {
			throw new DHServiceException(MISSING_FIELD, "fromDate");
		}
	}
}
