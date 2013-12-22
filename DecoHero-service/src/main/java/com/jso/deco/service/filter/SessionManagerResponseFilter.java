package com.jso.deco.service.filter;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.jso.deco.service.session.SessionManager;

public class SessionManagerResponseFilter implements ContainerResponseFilter {

	private static final String SET_COOKIE = "Set-Cookie";

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if(SessionManager.getInstance().isAuthenticated()) {
			SessionManager.getInstance().getSession().refreshExpiredDate();
		}
		responseContext.getHeaders().add(SET_COOKIE, SessionManager.getInstance().getCookie(requestContext));
		
		SessionManager.getInstance().clearSession();

		List<String> origin = requestContext.getHeaders().get("origin");
		if(origin != null && !origin.isEmpty()) {
			responseContext.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaders().get("origin").get(0));
		}
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	}
	
}
