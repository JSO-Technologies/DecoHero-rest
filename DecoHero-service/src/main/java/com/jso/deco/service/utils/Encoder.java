package com.jso.deco.service.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(Encoder.class);
	
	private final Base64 base64;
	private MessageDigest digestSHA1;
	
	public Encoder() {
		base64 = new Base64();
		try {
			digestSHA1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error during Encoder init : " + e.getMessage(), e);
			digestSHA1 = null;
		}
	}

	public byte[] decodeBase64(String encoded) {
		return base64.decode(encoded);
	}

	public byte[] encodeBase64(byte[] decoded) {
		return base64.encode(decoded);
	}

	public byte[] digestSHA1(byte[] value) {
		return digestSHA1.digest(value);
	}
}
