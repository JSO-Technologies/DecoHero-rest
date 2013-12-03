package com.jso.deco.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;

import com.jso.deco.session.SessionManager;

public class SessionManagerRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		SessionManager.getInstance().clearSession();
		
		Cookie cookie = requestContext.getCookies().get(SessionManager.DECO_COOKIE);
		if (cookie != null) {
			SessionManager.getInstance().bind(cookie.getValue());
		}
	}
	
}
