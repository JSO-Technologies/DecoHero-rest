package com.jso.deco.service.project;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static com.jso.deco.api.exception.DHMessageCode.MISSING_FIELD;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;


public class ProjectServiceValidatorTest {
	private final ProjectServiceValidator validator = new ProjectServiceValidator();
	
	@Test
	public void validate_project_id_should_pass() throws DHServiceException {
		//given
		String projectId = "azliheahze";
		
		//when
		validator.validate(projectId);
		
		//then
		
	}
	
	@Test
	public void validate_project_id_should_throw_exception_when_id_is_null() {
		//given
		String projectId = null;
		
		//when
		try {
			validator.validate(projectId);
			fail("Should throw exception");
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
		}
	}
	
	@Test
	public void validate_project_id_should_throw_exception_when_id_is_blank() {
		//given
		String projectId = " ";
		
		//when
		try {
			validator.validate(projectId);
			fail("Should throw exception");
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
		}
	}
	
	@Test
	public void validate_project_creation_request_should_pass() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		
		//when
		validator.validate(request);
		
		//then
	}
	
	@Test
	public void validate_project_creation_request_should_throw_exception_coz_category() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		request.setCategory(null);
		
		//when
		try {
			validator.validate(request);
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("category");
		}
	}
	
	@Test
	public void validate_project_creation_request_should_throw_exception_coz_room() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		request.setRoom(null);
		
		//when
		try {
			validator.validate(request);
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("room");
		}
	}
	
	@Test
	public void validate_project_creation_request_should_throw_exception_coz_title() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		request.setTitle(null);
		
		//when
		try {
			validator.validate(request);
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("title");
		}
	}
	
	@Test
	public void validate_project_creation_request_should_throw_exception_coz_description() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		request.setDescription(null);
		
		//when
		try {
			validator.validate(request);
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("description");
		}
	}
	
	@Test
	public void validate_project_creation_request_should_throw_exception_coz_images() throws DHServiceException {
		//given
		ProjectCreationRequest request = defaultProjectCreationRequest();
		request.setImages(null);
		
		//when
		try {
			validator.validate(request);
		}
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("images");
		}
	}
	
	private ProjectCreationRequest defaultProjectCreationRequest() {
		ProjectCreationRequest request = new ProjectCreationRequest();
		request.setCategory(RM);
		request.setRoom(ATC);
		request.setTitle("title");
		request.setDescription("description");
		request.setImages(Lists.newArrayList("azearhrhg", "sdkfhsdkfh"));
		
		return request;
	}
}
