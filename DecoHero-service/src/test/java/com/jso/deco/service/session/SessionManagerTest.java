package com.jso.deco.service.session;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URISyntaxException;

import javax.ws.rs.core.NewCookie;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.jso.deco.test.utils.RequestUtils;


public class SessionManagerTest {
	private SessionManager sessionManager = SessionManager.getInstance();
	
	@Before
	public void init() {
		sessionManager.clearSession();
	}
	
	@Test
	public void bind_should_bind_valid_session() throws URISyntaxException {
		//given
		Session session = new Session("123");
		NewCookie cookie = CookieEncoder.getInstance().sessionToCookie(session, RequestUtils.getMockedRequestContext("http://localhost"));
		
		//when
		sessionManager.bind(cookie.getValue());
		
		//then
		assertThat(sessionManager.isAuthenticated()).isTrue();
		Session storedSession = sessionManager.getSession();
		assertThat(storedSession.getUserId()).isEqualTo("123");
		assertThat(storedSession.isValid()).isTrue();
	}
	
	@Test
	public void bind_should_not_bind_session_coz_expired() throws URISyntaxException {
		//given
		DateTime expireDate = new DateTime().minusHours(1);
		Session session = new Session("123", expireDate.getMillis());
		NewCookie cookie = CookieEncoder.getInstance().sessionToCookie(session, RequestUtils.getMockedRequestContext("http://localhost"));
		
		//when
		sessionManager.bind(cookie.getValue());
		
		//then
		assertThat(sessionManager.isAuthenticated()).isFalse();
	}
	
	@Test
	public void bind_should_not_bind_invalid_session() throws URISyntaxException {
		//given
		String decoded_cookie_value = "tLJqS78ju2pK8cqlpaVw9/Kphvw=;123;1387926000000";
		Session session = Session.fromSerialized(decoded_cookie_value);
		
		NewCookie cookie = CookieEncoder.getInstance().sessionToCookie(session, RequestUtils.getMockedRequestContext("http://localhost"));
		
		//when
		sessionManager.bind(cookie.getValue());
		
		//then
		assertThat(sessionManager.isAuthenticated()).isFalse();
	}
	
	@Test
	public void clearSession_must_delete_session_from_thread() {
		//given
		sessionManager.setSession(new Session("123"));
		
		//when
		sessionManager.clearSession();
		
		//then
		assertThat(sessionManager.getSession()).isNull();
	}
	
	@Test
	public void isAuthenticated_must_return_true() {
		//given
		sessionManager.setSession(new Session("123"));
		
		//when
		boolean isAuthenticated = sessionManager.isAuthenticated();
		
		//then
		assertThat(isAuthenticated).isTrue();
	}
	
	@Test
	public void isAuthenticated_must_return_false() {
		//given
		
		//when
		boolean isAuthenticated = sessionManager.isAuthenticated();
		
		//then
		assertThat(isAuthenticated).isFalse();
	}
}
