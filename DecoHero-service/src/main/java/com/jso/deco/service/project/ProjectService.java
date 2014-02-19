package com.jso.deco.service.project;

import java.io.ByteArrayInputStream;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.ProjectResponse;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{projectId}")
	public Response getProject(@PathParam("projectId") String projectId) {
		try {
			validator.validate(projectId);
			
			ProjectResponse response = controller.getProject(projectId);
			
			return Response.status(HttpStatus.OK.value()).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
	@GET
	@Produces("image/png")
	@Path("/image/{projectId}/{imageId}")
	public Response getImage(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId) {
		byte[] imageData = controller.getImage(projectId, imageId);
		
		if(imageData == null) {
			return Response.status(HttpStatus.NOT_FOUND.value()).build();
		}
		else {
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/latest/{userId}")
	public Response getLatestProjects(@PathParam("userId") String userId) {
		return getLatestProjects(userId, null);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/latest/{userId}/{fromDate}")
	public Response getLatestProjects(@PathParam("userId") String userId, @PathParam("fromDate") String fromDate) {
		try {
			validator.validate(userId, fromDate);
			
			LatestProjectResponse response = controller.getLastestProjects(userId, fromDate);
			
			return Response.status(HttpStatus.OK.value()).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
}
