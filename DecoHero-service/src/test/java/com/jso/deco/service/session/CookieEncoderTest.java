package com.jso.deco.service.session;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.NewCookie;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.junit.Test;

import com.jso.deco.test.utils.RequestUtils;


public class CookieEncoderTest {
	private CookieEncoder cookieEncoder = CookieEncoder.getInstance();

	@Test
	public void hashFromSession_should_encode_session_to_correct_hash() throws NoSuchAlgorithmException {
		//given
		DateTime date = new DateTime();
		Session session = new Session("123", date.getMillis());

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
		Session session = new Session("123", new DateTime().getMillis());
		NewCookie cookie = cookieEncoder.sessionToCookie(session, RequestUtils.getMockedRequestContext("http://localhost"));

		//when
		Session resultingSession = cookieEncoder.cookieToSession(cookie.getValue());

		//then
		assertThat(resultingSession.getUserId()).isEqualTo(session.getUserId());
		assertThat(resultingSession.getExpirationTime()).isEqualTo(session.getExpirationTime());
		assertThat(resultingSession.getHash()).isEqualTo(session.getHash());
	}
	
	@Test
	public void cookieToSession_should_deserialize_cookie_value() throws URISyntaxException, IOException, NoSuchAlgorithmException {
		//given
		long expirationTime = new DateTime(2013, 12, 25, 0, 0, 0).getMillis();
		String cookieValue = cookieValue("123", expirationTime);
		String expectedHash = cookieHash("123", expirationTime);

		//when
		Session resultingSession = cookieEncoder.cookieToSession(cookieValue);

		//then
		assertThat(resultingSession.getUserId()).isEqualTo("123");
		assertThat(resultingSession.getExpirationTime()).isEqualTo(expirationTime);
		assertThat(resultingSession.getHash()).isEqualTo(expectedHash);
	}

	@Test
	public void sessionToCookie_should_serialize_session() throws URISyntaxException, NoSuchAlgorithmException {
		//given
		long expirationTime = new DateTime(2013, 12, 25, 0, 0, 0).getMillis();
		Session session = new Session("123", expirationTime);
		
		String expectedValue = cookieValue("123", expirationTime);

		//when
		NewCookie resultingCookie = cookieEncoder.sessionToCookie(session, RequestUtils.getMockedRequestContext("http://localhost"));

		//then
		assertThat(resultingCookie.getName()).isEqualTo(SessionManager.DECO_COOKIE);
		assertThat(resultingCookie.getValue()).isEqualTo(expectedValue);
		assertThat(resultingCookie.getDomain()).isEqualTo("localhost");
		assertThat(resultingCookie.getPath()).isEqualTo("/");
		assertThat(resultingCookie.getVersion()).isEqualTo(1);
	}
	
	private String cookieValue(String userId, long expirationTime) throws NoSuchAlgorithmException {
		String hash = cookieHash(userId, expirationTime);
		String toEncode = hash + ";" + userId + ";" + expirationTime;
		return new String(Base64.encodeBase64(toEncode.getBytes()));
	}
	
	private String cookieHash(String userId, long expirationTime) throws NoSuchAlgorithmException {
		String toEncode = expirationTime + ";" + userId + ";decoherodecohero";
		byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(toEncode.getBytes());
		return new String(Base64.encodeBase64(sha1));
	}

}
