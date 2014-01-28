package com.jso.deco.service.project;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.api.service.response.ServiceResponse;
import com.jso.deco.controller.ProjectController;
import com.jso.deco.service.adapter.ServiceResponseAdapter;
import com.jso.deco.service.session.SessionManager;

@Path("project")
public class ProjectService {
	@Autowired
	private ProjectController controller;

	@Autowired
	private ProjectServiceValidator validator;

	@Autowired
	private ServiceResponseAdapter errorAdapter;

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createProject(@BeanParam ProjectCreationRequest request) {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(HttpStatus.UNAUTHORIZED.value()).build();
		}
		
		try {
			validator.validate(request);
			CreateProjectResponse response = controller.createProject(SessionManager.getInstance().getSession().getUserId(), request);
			
			return Response.status(HttpStatus.OK.value()).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
}
