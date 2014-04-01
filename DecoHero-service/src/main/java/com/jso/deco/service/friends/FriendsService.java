package com.jso.deco.service.friends;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.jso.deco.api.controller.FriendsListResponse;
import com.jso.deco.controller.FriendsController;
import com.jso.deco.service.session.SessionManager;

@Path("friends")
public class FriendsService {
	@Autowired
	private FriendsController controller;
	
	@GET
	public Response getSession() {
		if(! SessionManager.getInstance().isAuthenticated()) {
			return Response.status(UNAUTHORIZED).build();
		}
		
		final String userId = SessionManager.getInstance().getSession().getUserId();
		final FriendsListResponse response = controller.getFriends(userId);
		
		return Response.status(OK).entity(response).build();
	}
}
