package com.jso.deco.service.session;

import static org.fest.assertions.Assertions.assertThat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.junit.Test;


public class SessionTest {
	@Test
	public void hash_should_be_set_in_constructor() throws NoSuchAlgorithmException {
		//given
		long expirationTime = new DateTime(2013, 12, 25, 0, 0, 0).getMillis();
		String expectedHash = cookieHash("123", expirationTime);
		
		//when
		Session session = new Session("123", expirationTime);
		
		//then
		assertThat(session.getHash()).isEqualTo(expectedHash);
		
	}
	
	@Test
	public void constructor_from_cookie_value() {
		//given
		String decoded_cookie_value = "tLJqS78ju2pK8cqlpaVw8/Kphvw=;123;1387926000000";
		
		//when
		Session session = Session.fromSerialized(decoded_cookie_value);
		
		//then
		assertThat(session.getUserId()).isEqualTo("123");
		assertThat(session.getExpirationTime()).isEqualTo(1387926000000L);
		assertThat(session.getHash()).isEqualTo("tLJqS78ju2pK8cqlpaVw8/Kphvw=");
		
	}
	
	@Test
	public void isExpired_should_return_true() {
		//given
		DateTime expirationTime = new DateTime();
		expirationTime = expirationTime.minusHours(1);
		Session session = new Session("123", expirationTime.getMillis());
		
		//when
		boolean isExpired = session.isExpired();
		
		//then
		assertThat(isExpired).isTrue();
	}
	
	@Test
	public void isExpired_should_return_false() {
		//given
		DateTime expirationTime = new DateTime();
		expirationTime = expirationTime.plusHours(1);
		Session session = new Session("123", expirationTime.getMillis());
		
		//when
		boolean isExpired = session.isExpired();
		
		//then
		assertThat(isExpired).isFalse();
	}
	
	@Test
	public void isValid_should_return_true() {
		//given
		DateTime expireDate = new DateTime().plusHours(1);
		
		Session session = new Session("123", expireDate.getMillis());
		
		//when
		boolean isValid = session.isValid();
		
		//then
		assertThat(isValid).isTrue();
	}
	
	@Test
	public void isValid_should_return_false_coz_hash() {
		//given
		DateTime expireDate = new DateTime().plusHours(1);
		
		String decoded_cookie_value = "AZERTY;123;" + expireDate.getMillis();
		Session session = Session.fromSerialized(decoded_cookie_value);
		
		//when
		boolean isValid = session.isValid();
		
		//then
		assertThat(isValid).isFalse();
	}
	
	@Test
	public void isValid_should_return_false_coz_expiration_time() {
		//given
		DateTime expireDate = new DateTime().minusHours(1);
		
		Session session = new Session("123", expireDate.getMillis());
		
		//when
		boolean isValid = session.isValid();
		
		//then
		assertThat(isValid).isFalse();
	}
	
	private String cookieHash(String userId, long expirationTime) throws NoSuchAlgorithmException {
		String toEncode = expirationTime + ";" + userId + ";decoherodecohero";
		byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(toEncode.getBytes());
		return new String(Base64.encodeBase64(sha1));
	}
}
