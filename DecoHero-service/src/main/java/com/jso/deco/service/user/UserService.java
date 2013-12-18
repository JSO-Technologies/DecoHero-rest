package com.jso.deco.service.user;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.jso.deco.api.adapter.UserAdapter;
import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.api.service.response.ServiceResponse;
import com.jso.deco.api.service.response.UserLoginResponse;
import com.jso.deco.controller.UserController;
import com.jso.deco.service.adapter.ServiceResponseAdapter;
import com.jso.deco.service.session.Session;
import com.jso.deco.service.session.SessionManager;
import com.jso.deco.service.utils.Encoder;

@Path("user")
public class UserService {
	@Autowired
	private UserController controller;
	
	@Autowired
	private Encoder encoder;
	
	@Autowired
	private UserServiceValidator validator;
	
	@Autowired
	private UserAdapter userAdapter;
	
	@Autowired
	private ServiceResponseAdapter errorAdapter;
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/register")
    public Response createUser(@FormParam("username") String username, @FormParam("firstname") String firstname, @FormParam("lastname") String lastname, @FormParam("email") String email, @FormParam("password") String password, @FormParam("birthdate") Long birthdate) {
    	UserResgisterRequest request = new UserResgisterRequest();
    	request.setUsername(username);
    	request.setFirstname(firstname);
    	request.setLastname(lastname);
    	request.setEmail(email);
    	request.setPassword(password);
    	request.setBirthdate(birthdate == null ? null : new Date(birthdate));
    	
    	try {
    		validator.validateCreationValues(request);

    		request.setPassword(new String(encoder.digestSHA1(password.getBytes())));
    		DBUser dbUser = controller.createUser(request);
    		
    		UserLoginResponse loginResponse = userAdapter.dbUserToUserResponse(dbUser);
    		
    		Session session = new Session(dbUser.getId());
    		SessionManager.getInstance().setSession(session);
    		
    		return Response.status(HttpStatus.OK.value()).entity(loginResponse).build();
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
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
    	UserLoginRequest request = new UserLoginRequest();
    	request.setEmail(email);
    	request.setPassword(password);
    	
    	try {
    		validator.validateLoginValues(request);

    		request.setPassword(new String(encoder.digestSHA1(password.getBytes())));
    		DBUser dbUser = controller.login(request);
    		
    		UserLoginResponse loginResponse = userAdapter.dbUserToUserResponse(dbUser);
    		
    		Session session = new Session(dbUser.getId());
    		SessionManager.getInstance().setSession(session);
    		
    		return Response.status(HttpStatus.OK.value()).entity(loginResponse).build();
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
    		return Response.status(HttpStatus.OK.value()).build();
    	}
    	else {
    		return Response.status(HttpStatus.UNAUTHORIZED.value()).build();
    	}
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/session")
    public Response logout() {
    	SessionManager.getInstance().clearSession();
    	return Response.status(200).build();
    }
}
