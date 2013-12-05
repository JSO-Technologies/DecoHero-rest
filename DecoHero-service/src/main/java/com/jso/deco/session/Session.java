package com.jso.deco.session;

import java.util.Arrays;

import org.joda.time.DateTime;

public class Session {
	private static final String SERIALIZATION_SEPARATOR = ";";
	private static final int DURATION_IN_MINUTES = 60;
	private final String userId;
	private long expirationTime;
	private String hash;
	
	public Session(final String userId) {
		this.userId = userId;
		refreshExpiredDate();
	}
	
	public Session(final String userId, final long expirationTime) {
		this.userId = userId;
		this.expirationTime = expirationTime;
		refreshHash();
	}
	
	private Session(final String userId, final long expirationTime, final String hash) {
		this.userId = userId;
		this.expirationTime = expirationTime;
		this.hash = hash;
	}
	
	public static Session fromSerialized(String serializedSession) {
		String[] parts = serializedSession.split(SERIALIZATION_SEPARATOR);
		if(parts.length != 3) {
			throw new IllegalArgumentException();
		}
		
		String hash = parts[0];
		String userId = parts[1];
		Long expirationTime = new Long(parts[2]);
		return new Session(userId, expirationTime, hash);
	}

	public boolean isExpired() {
		return expirationTime < System.currentTimeMillis();
	}

	public void refreshExpiredDate() {
		DateTime now = new DateTime();
		expirationTime = now.plusMinutes(DURATION_IN_MINUTES).getMillis();
		refreshHash();
	}
	
	private void refreshHash() {
		CookieEncoder cookieEncoder = CookieEncoder.getInstance();
		this.hash = new String(cookieEncoder.encodeBase64(cookieEncoder.hashFromSession(this)));
	}
	
	public String getUserId() {
		return userId;
	}
	
	public long getExpirationTime() {
		return expirationTime;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String toString() {
		return hash + SERIALIZATION_SEPARATOR + userId + SERIALIZATION_SEPARATOR + expirationTime;
	}

	public boolean isValid() {
		if(isExpired()) {
			return false;
		}
		
		byte[] calculatedHash = CookieEncoder.getInstance().hashFromSession(this);
		byte[] currentHash = CookieEncoder.getInstance().decodeBase64(hash);
		return Arrays.equals(calculatedHash, currentHash);
	}

}
