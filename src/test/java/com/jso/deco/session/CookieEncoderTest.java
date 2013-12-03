package com.jso.deco.session;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.NewCookie;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.joda.time.DateTime;
import org.junit.Test;


public class CookieEncoderTest {
	private CookieEncoder cookieEncoder = CookieEncoder.getInstance();

	@Test
	public void hashFromSession_should_encode_session_to_correct_hash() throws NoSuchAlgorithmException {
		//given
		DateTime date = new DateTime();
		Session session = new Session(123L, date.getMillis());

		String serializedSession = session.getExpirationTime() + ";" + session.getUserId() + ";decoherodecohero";
		byte[] expectedHash = MessageDigest.getInstance("SHA-1").digest(serializedSession.getBytes());

		//when
		byte[] hash = cookieEncoder.hashFromSession(session);

		//then
		assertThat(hash).isEqualTo(expectedHash);
	}

	@Test
	public void cookieToSession_should_deserialize_sessionToCookie_result() throws URISyntaxException, IOException {
		//given
		Session session = new Session(123L, new DateTime().getMillis());
		NewCookie cookie = cookieEncoder.sessionToCookie(session, getMockedRequestContext("http://localhost"));

		//when
		Session resultingSession = cookieEncoder.cookieToSession(cookie.getValue());

		//then
		assertThat(resultingSession.getUserId()).isEqualTo(session.getUserId());
		assertThat(resultingSession.getExpirationTime()).isEqualTo(session.getExpirationTime());
		assertThat(resultingSession.getHash()).isEqualTo(session.getHash());
	}
	
	@Test
	public void cookieToSession_should_deserialize_cookie_value() throws URISyntaxException, IOException {
		//given
		String cookie_value = "dExKcVM3OGp1MnBLOGNxbHBhVnc4L0twaHZ3PTsxMjM7MTM4NzkyNjAwMDAwMA==";

		//when
		Session resultingSession = cookieEncoder.cookieToSession(cookie_value);

		//then
		assertThat(resultingSession.getUserId()).isEqualTo(123L);
		assertThat(resultingSession.getExpirationTime()).isEqualTo(new DateTime(2013, 12, 25, 0, 0, 0).getMillis());
		assertThat(resultingSession.getHash()).isEqualTo("tLJqS78ju2pK8cqlpaVw8/Kphvw=");
	}

	@Test
	public void sessionToCookie_should_serialize_session() throws URISyntaxException {
		//given
		Session session = new Session(123L, new DateTime(2013, 12, 25, 0, 0, 0).getMillis());

		//when
		NewCookie resultingCookie = cookieEncoder.sessionToCookie(session, getMockedRequestContext("http://localhost"));

		//then
		assertThat(resultingCookie.getName()).isEqualTo(SessionManager.DECO_COOKIE);
		assertThat(resultingCookie.getValue()).isEqualTo("dExKcVM3OGp1MnBLOGNxbHBhVnc4L0twaHZ3PTsxMjM7MTM4NzkyNjAwMDAwMA==");
		assertThat(resultingCookie.getDomain()).isEqualTo("localhost");
		assertThat(resultingCookie.getPath()).isEqualTo("/");
		assertThat(resultingCookie.getVersion()).isEqualTo(1);
	}

	private ContainerRequestContext getMockedRequestContext(String uri) throws URISyntaxException {
		ExtendedUriInfo uriInfo = mock(UriRoutingContext.class);
		ContainerRequestContext requestContext = mock(ContainerRequest.class);
		when(requestContext.getUriInfo()).thenReturn(uriInfo);
		when(uriInfo.getBaseUri()).thenReturn(new URI(uri));
		return requestContext;
	}
}
