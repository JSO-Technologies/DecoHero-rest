package com.jso.deco.service.user;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.api.controller.UpdateAvatarResponse;
import com.jso.deco.api.controller.UserInfosResponse;
import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.controller.UserPublicInfosResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserInfosRequest;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.api.service.response.ServiceResponse;
import com.jso.deco.controller.UserController;
import com.jso.deco.service.adapter.ServiceResponseAdapter;
import com.jso.deco.service.adapter.UserServiceAdapter;
import com.jso.deco.service.session.Session;
import com.jso.deco.service.session.SessionManager;

@Path("user")
public class UserService {
	@Autowired
	private UserController controller;

	@Autowired
	private UserServiceValidator validator;

	@Autowired
	private UserServiceAdapter userAdapter;

	@Autowired
	private ServiceResponseAdapter errorAdapter;

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/register")
	public Response createUser(@BeanParam UserRegisterRequest request) {

		try {
			validator.validate(request);
			userAdapter.adapt(request);

			UserLoginResponse loginResponse = controller.createUser(request);

			Session session = new Session(loginResponse.getId());
			SessionManager.getInstance().setSession(session);

			return Response.status(OK).entity(loginResponse).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/session")
	public Response login(@BeanParam UserLoginRequest request) {

		try {
			validator.validate(request);
			userAdapter.adapt(request);

			UserLoginResponse loginResponse = controller.login(request);

			Session session = new Session(loginResponse.getId());
			SessionManager.getInstance().setSession(session);

			return Response.status(OK).entity(loginResponse).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}

	@GET
	@Path("/session")
	public Response getSession() {
		if(SessionManager.getInstance().isAuthenticated()) {
			return Response.status(OK).build();
		}
		else {
			return Response.status(NOT_FOUND).build();
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/session")
	public Response logout() {
		SessionManager.getInstance().clearSession();
		return Response.status(200).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/infos")
	public Response getUserInfos() {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}

		try {			
			String userId = SessionManager.getInstance().getSession().getUserId();

			UserInfosResponse response = controller.getUserInfos(userId, true);

			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/infos")
	public Response updateUserInfos(@BeanParam UserInfosRequest request) {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}

		try {
			validator.validate(request);
			userAdapter.adapt(request);

			String userId = SessionManager.getInstance().getSession().getUserId();

			controller.updateUserInfos(userId, request);

			return Response.status(OK).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/infos/{userid}")
	public Response getUserInfos(@PathParam("userid") String userid) {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}

		try {			
			String userId = SessionManager.getInstance().getSession().getUserId();

			UserPublicInfosResponse response = controller.getUserInfos(userId, false);

			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/infos/avatar")
	public Response updateAvatar(@FormParam("avatar") String avatarDataUrl) {
		if(!SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}
		
		try {
			validator.validatePngImageDataUrl(avatarDataUrl);
			
			final String userId = SessionManager.getInstance().getSession().getUserId();
			UpdateAvatarResponse response = controller.updateAvatar(userId, avatarDataUrl);
			
			return Response.status(OK).entity(response).build();
		}
		catch(DHServiceException e) {
			ServiceResponse response = errorAdapter.fromException(e);
			return Response.status(response.getStatus()).entity(response.getContent()).build();
		}
	}
	
}
