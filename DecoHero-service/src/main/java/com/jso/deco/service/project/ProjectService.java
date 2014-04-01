package com.jso.deco.service.project;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.ByteArrayInputStream;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.controller.LatestProjectIdeasResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.ProjectIdeaResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.api.service.request.ProjectIdeaCreationRequest;
import com.jso.deco.api.service.response.ServiceResponse;
import com.jso.deco.controller.ProjectController;
import com.jso.deco.controller.image.ImageSize;
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
			return Response.status(UNAUTHORIZED).build();
		}
		
		try {
			validator.validate(request);
			CreateProjectResponse response = controller.createProject(SessionManager.getInstance().getSession().getUserId(), request);
			
			return Response.status(OK).entity(response).build();
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
			
			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
	@GET
	@Produces("image/png")
	@Path("/image/{projectId}/{imageId}")
	public Response getImage(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId, @QueryParam("size") ImageSize size) {
		byte[] imageData = controller.getImage(projectId, imageId, size);
		
		if(imageData == null) {
			return Response.status(NOT_FOUND).build();
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
			validator.validate("userId", userId, fromDate);
			
			LatestProjectResponse response = controller.getLastestProjects(userId, fromDate);
			
			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{projectId}/ideas")
	public Response createProjectIdea(@PathParam("projectId") String projectId, @BeanParam ProjectIdeaCreationRequest request) {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}
		
		try {
			validator.validate(projectId);
			validator.validate(request);
			
			final String userId = SessionManager.getInstance().getSession().getUserId();
			final ProjectIdeaResponse response = controller.createProjectIdea(userId , projectId, request);
			
			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{projectId}/ideas")
	public Response getLastestIdeas(@PathParam("projectId") String projectId) {
		return getLastestIdeas(projectId, null);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{projectId}/ideas/{fromDate}")
	public Response getLastestIdeas(@PathParam("projectId") String projectId, @PathParam("fromDate") String fromDate) {
		try {
			validator.validate("projectId", projectId, fromDate);
			
			final LatestProjectIdeasResponse response = controller.getLatestProjectIdeas(projectId, fromDate);
			
			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
	@GET
	@Produces("image/png")
	@Path("/idea/image/{projectId}/{ideaId}/{imageId}")
	public Response getImage(@PathParam("projectId") String projectId, @PathParam("ideaId") String ideaId, @PathParam("imageId") String imageId, @QueryParam("size") ImageSize size) {
		byte[] imageData = controller.getImage(projectId, ideaId, imageId, size);
		
		if(imageData == null) {
			return Response.status(NOT_FOUND).build();
		}
		else {
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		}
	}
}
