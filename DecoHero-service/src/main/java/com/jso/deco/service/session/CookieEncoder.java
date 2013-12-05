package com.jso.deco.service.session;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.codec.binary.Base64;

import com.jso.deco.service.utils.Encoder;

public class CookieEncoder {
	private static CookieEncoder INSTANCE = new CookieEncoder();
	private static final String HASH_SALT = "decoherodecohero";
	private final Encoder encoder;

	private CookieEncoder() {
		encoder = new Encoder();
	}

	public static CookieEncoder getInstance() {
		return INSTANCE;
	}

	public Session cookieToSession(String cookie_value) throws IOException {
		byte[] value = encoder.decodeBase64(cookie_value);
		try {
			return Session.fromSerialized(new String(value));
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	public NewCookie sessionToCookie(Session session, ContainerRequestContext requestContext) {
		String token_value = new String(Base64.encodeBase64(session.toString().getBytes()));

		return new NewCookie(
				SessionManager.DECO_COOKIE, 
				token_value, 
				"/", 
				requestContext.getUriInfo().getBaseUri().getHost(), 
				1,
				null, 
				(int) (session.getExpirationTime() - System.currentTimeMillis()) / 1000,
				new Date(session.getExpirationTime()),
				false, //is secured 
				true);
	}

	public byte[] base64HashFromSession(Session session) {
		return encoder.encodeBase64(hashFromSession(session));
	}

	public byte[] hashFromSession(Session session) {
		String value = session.getExpirationTime() + ";" + session.getUserId() + ";" + HASH_SALT;
		return encoder.digestSHA1(value.getBytes());
	}
}
