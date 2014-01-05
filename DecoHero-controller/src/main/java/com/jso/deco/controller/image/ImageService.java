package com.jso.deco.controller.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;


public class ImageService {
	private static final String ENCODING_PREFIX = "base64,";
	private final Base64 base64 = new Base64();
	private String imageLocation = "/home/jsomsanith";
	
	public void saveAvatar(String imageEncodedId, String avatarDataUrl) {
		final String uploadedFileLocation = imageLocation + "/" + imageEncodedId + ".png";
		
		int contentStartIndex = avatarDataUrl.indexOf(ENCODING_PREFIX) + ENCODING_PREFIX.length();
		byte[] imageData = base64.decode(avatarDataUrl.substring(contentStartIndex).getBytes());
		
		saveToFile(new ByteArrayInputStream(imageData), uploadedFileLocation);
	}

	// save uploaded file to new location
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
			e.printStackTrace();
		}
	}

}
