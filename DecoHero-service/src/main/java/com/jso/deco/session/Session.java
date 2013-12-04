package com.jso.deco.session;

import java.util.Arrays;

import org.joda.time.DateTime;

public class Session {
	private static final String SERIALIZATION_SEPARATOR = ";";
	private static final int DURATION_IN_MINUTES = 60;
	private final Long userId;
	private long expirationTime;
	private String hash;
	
	public Session(final Long userId) {
		this.userId = userId;
		refreshExpiredDate();
	}
	
	public Session(final Long userId, final long expirationTime) {
		this.userId = userId;
		this.expirationTime = expirationTime;
		refreshHash();
	}
	
	public Session(String serializedSession) {
		String[] parts = serializedSession.split(SERIALIZATION_SEPARATOR);
		if(parts.length != 3) {
			throw new IllegalArgumentException();
		}
		
		this.userId = new Long(parts[1]);
		this.expirationTime = new Long(parts[2]);
		this.hash = parts[0];
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
	
	public Long getUserId() {
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
