package com.jso.deco.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.jso.deco.session.SessionManager;

public class SessionManagerResponseFilter implements ContainerResponseFilter {

	private static final String SET_COOKIE = "Set-Cookie";

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if(SessionManager.getInstance().isAuthenticated()) {
			SessionManager.getInstance().getSession().refreshExpiredDate();
			responseContext.getHeaders().add(SET_COOKIE, SessionManager.getInstance().getCookie(requestContext));
		}
		
		SessionManager.getInstance().clearSession();
	}
	
}
