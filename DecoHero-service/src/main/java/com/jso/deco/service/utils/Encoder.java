package com.jso.deco.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class Encoder {
	private final Base64 base64;
	private MessageDigest digestSHA1;
	
	public Encoder() {
		base64 = new Base64();
		try {
			digestSHA1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
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
