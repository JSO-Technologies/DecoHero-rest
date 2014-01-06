package com.jso.deco.service.session;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.NewCookie;

public class SessionManager {
	public static final String DECO_COOKIE = "deco_cookie";

	private static final SessionManager INSTANCE = new SessionManager();
	

	private final ThreadLocal<Session> threadSession = new ThreadLocal<Session>() {

		@Override
		protected Session initialValue() {
			return null;
		}

	};
	
	private SessionManager() {}
	
	public static SessionManager getInstance() {
		return INSTANCE;
	}
	
	public Session getSession() {
		return threadSession.get();
	}
	
	public void clearSession() {
		threadSession.set(null);
	}
	
	public void setSession(Session session) {
		threadSession.set(session);
	}
	
	/**
	 * Retrieve session from cookie value and put it in current thread
	 * @param encodedValue
	 */
	public void bind(String encodedValue) {
		Session session = CookieEncoder.getInstance().cookieToSession(encodedValue);
		
		if(session != null && session.isValid()) {
			threadSession.set(session);
		}
	}

	/**
	 * Return if there is a session in the current thread
	 * @return
	 */
	public boolean isAuthenticated() {
		return getSession() != null;
	}

	/**
	 * Create cookie from session
	 * @param requestContext
	 * @return
	 */
	public NewCookie getCookie(ContainerRequestContext requestContext) {
		return CookieEncoder.getInstance().sessionToCookie(getSession(), requestContext);
	}
	
}
