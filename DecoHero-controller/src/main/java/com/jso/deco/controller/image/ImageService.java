package com.jso.deco.controller.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImageService {
	private static final String ENCODING_PREFIX = "base64,";	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	private final Base64 base64 = new Base64();
	private String avatarLocation;
	
	/**
	 * Save image from base 64 encoded string
	 * @param imageEncodedId
	 * @param avatarDataUrl
	 */
	public void saveAvatar(String imageEncodedId, String avatarDataUrl) {
		final String uploadedFileLocation = avatarLocation + imageEncodedId;
		
		int contentStartIndex = avatarDataUrl.indexOf(ENCODING_PREFIX) + ENCODING_PREFIX.length();
		byte[] imageData = base64.decode(avatarDataUrl.substring(contentStartIndex).getBytes());
		
		saveToFile(new ByteArrayInputStream(imageData), uploadedFileLocation);
	}

	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("Error while saving avatar in " + uploadedFileLocation + " : " + e.getMessage(), e);
		}
	}

	/**
	 * Get avatar byte array
	 * @param imageUrl
	 * @return
	 */
	public byte[] getAvatar(String imageUrl) {
	    try {
			File avatarFile = new File(avatarLocation + imageUrl);
			return Files.readAllBytes(avatarFile.toPath());
		} catch (IOException e) {
			LOGGER.error("Error while reading avatar " + avatarLocation + imageUrl + " : " + e.getMessage(), e);
			return null;
		}
	}

	public void setAvatarLocation(String avatarLocation) {
		this.avatarLocation = avatarLocation;
	}
}
