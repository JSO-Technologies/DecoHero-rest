package com.jso.deco.user;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.api.service.User;
import com.jso.deco.controller.UserController;
import com.jso.deco.exception.DHServiceException;
import com.jso.deco.session.Session;
import com.jso.deco.session.SessionManager;
import com.jso.deco.utils.Encoder;

@Path("user")
public class UserService {
	@Autowired
	private UserController controller;
	
	@Autowired
	private Encoder encoder;
	
	@Autowired
	private UserValidator validator;
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/register")
    public Response getIt(@FormParam("username") String username, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email, @FormParam("password") String password, @FormParam("birthdate") Long birthDate) {
    	User user = new User();
    	user.setUsername(username);
    	user.setFirstName(firstName);
    	user.setLastName(lastName);
    	user.setEmail(email);
    	user.setPassword(password);
    	user.setBirthDate(new Date(birthDate));
    	
    	try {
    		validator.validateCreationValues(user);

    		user.setPassword(new String(encoder.digestSHA1(password.getBytes())));
    		controller.createUser(user);
    		
    		Session session = new Session(user.getId());
    		SessionManager.getInstance().setSession(session);
    		
    		return Response.status(200).entity(user).build();
    	}
    	catch(DHServiceException e) {
    		return Response.status(e.getHttpStatus().value()).entity(e.getDhMessage()).build();
    	}
    }
    
    public void setController(UserController controller) {
		this.controller = controller;
	}
}
