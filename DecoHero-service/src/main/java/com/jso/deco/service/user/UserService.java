package com.jso.deco.service.user;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.api.service.response.ServiceResponse;
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
	private ServiceResponseAdapter adapter;
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/register")
    public Response getIt(@FormParam("username") String username, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName, @FormParam("email") String email, @FormParam("password") String password, @FormParam("birthdate") Long birthDate) {
    	UserResgisterRequest request = new UserResgisterRequest();
    	request.setUsername(username);
    	request.setFirstName(firstName);
    	request.setLastName(lastName);
    	request.setEmail(email);
    	request.setPassword(password);
    	request.setBirthDate(birthDate == null ? null : new Date(birthDate));
    	
    	try {
    		validator.validateCreationValues(request);

    		request.setPassword(new String(encoder.digestSHA1(password.getBytes())));
    		String createdId = controller.createUser(request);
    		
    		Session session = new Session(createdId);
    		SessionManager.getInstance().setSession(session);
    		
    		return Response.status(200).build();
    	}
    	catch(DHServiceException e) {
    		ServiceResponse response = adapter.fromException(e);
    		return Response.status(response.getStatus()).entity(response.getContent()).build();
    	}
    }
}
