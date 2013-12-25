package com.jso.deco.service.user;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.exception.DHServiceException;
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
   
    @POST
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
    public Response login(@BeanParam UserLoginRequest request) {
    	
    	try {
    		validator.validate(request);
    		userAdapter.adapt(request);

    		UserLoginResponse loginResponse = controller.login(request);
    		
    		Session session = new Session(loginResponse.getId());
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
    		return Response.status(HttpStatus.NOT_FOUND.value()).build();
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
