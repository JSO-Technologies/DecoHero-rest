package com.jso.deco.service.image;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.ByteArrayInputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.controller.ProjectController;
import com.jso.deco.controller.UserController;
import com.jso.deco.controller.image.ImageSize;
import com.jso.deco.service.adapter.ServiceResponseAdapter;

@Path("public/image")
public class PublicImageService {
	@Autowired
	private UserController userController;
	@Autowired
	private ProjectController projectController;
	@Autowired
	private ServiceResponseAdapter errorAdapter;

	@GET
	@Produces("image/png")
	@Path("/avatar/{imageId}")
	public Response getAvatar(@PathParam("imageId") String imageId) {
		byte[] imageData = userController.getAvatar(imageId);
		
		if(imageData == null) {
			return Response.status(NOT_FOUND).build();
		}
		else {
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		}
	}
	
	@GET
	@Produces("image/png")
	@Path("/project/{projectId}/{imageId}")
	public Response getProjectImage(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId, @QueryParam("size") ImageSize size) {
		byte[] imageData = projectController.getImage(projectId, imageId, size);
		
		if(imageData == null) {
			return Response.status(NOT_FOUND).build();
		}
		else {
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		}
	}
	
	@GET
	@Produces("image/png")
	@Path("/idea/{projectId}/{ideaId}/{imageId}")
	public Response getIdeaImage(@PathParam("projectId") String projectId, @PathParam("ideaId") String ideaId, @PathParam("imageId") String imageId, @QueryParam("size") ImageSize size) {
		byte[] imageData = projectController.getImage(projectId, ideaId, imageId, size);
		
		if(imageData == null) {
			return Response.status(NOT_FOUND).build();
		}
		else {
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		}
	}
}
