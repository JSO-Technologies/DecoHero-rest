package com.jso.deco.controller.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;


public class ImageService {
	private static final String ENCODING_PREFIX = "base64,";	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	private final Base64 base64 = new Base64();
	private String avatarLocation;
	private String projectImgLocation;
	private String projectTmpImgLocation;
	
	/**
	 * Save image from base 64 encoded string
	 * @param imageEncodedId
	 * @param avatarDataUrl
	 * @throws DHServiceException 
	 */
	public void saveAvatar(String imageEncodedId, String avatarDataUrl) throws DHServiceException {
		final String uploadedFileLocation = avatarLocation + imageEncodedId;
		
		int contentStartIndex = avatarDataUrl.indexOf(ENCODING_PREFIX) + ENCODING_PREFIX.length();
		byte[] imageData = base64.decode(avatarDataUrl.substring(contentStartIndex).getBytes());
		
		saveToFile(new ByteArrayInputStream(imageData), uploadedFileLocation);
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
	
	/**
	 * Get avatar byte array
	 * @param imageUrl
	 * @return
	 */
	public byte[] getProjectImage(String projectId, String imageId) {
	    try {
			File imageFile = new File(projectImgLocation + projectId + "/" + imageId);
			return Files.readAllBytes(imageFile.toPath());
		} catch (IOException e) {
			LOGGER.error("Error while reading project image " + projectImgLocation + projectId + "/" + imageId + " : " + e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Save project images from dataUrls
	 * @param imgDataUrls
	 * @return
	 * @throws DHServiceException 
	 */
	public List<String> saveProjectImg(List<String> imgDataUrls) throws DHServiceException {
		final List<String> ids = Lists.newArrayListWithCapacity(imgDataUrls.size());
		
		for(String dataUrl : imgDataUrls) {
			String id = UUID.randomUUID().toString();
			ids.add(id);
			
			int contentStartIndex = dataUrl.indexOf(ENCODING_PREFIX) + ENCODING_PREFIX.length();
			byte[] imageData = base64.decode(dataUrl.substring(contentStartIndex).getBytes());			
			saveToFile(new ByteArrayInputStream(imageData), projectTmpImgLocation + id);
		}
		
		return ids;
	}
	
	/**
	 * Move project images into folder with project id name
	 * @param projectId
	 * @param imgDataUrls
	 */
	public void moveProjectImg(String projectId, List<String> imgIds) {
		String projectFolder = projectImgLocation + "/" + projectId + "/";
		createFolderIfNeeded(projectFolder);
		for(String imgId : imgIds) {
			File file = new File(projectTmpImgLocation + imgId);
			file.renameTo(new File(projectFolder + imgId));
		}
	}
	
	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws DHServiceException {
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
			throw new DHServiceException(DHMessageCode.MISSING_FIELD, "");
		}
	}

	private void createFolderIfNeeded(String folderLocation) {
		File folder = new File(folderLocation);
		if(! folder.exists()) {
			folder.mkdirs();
		}
	}

	public void setAvatarLocation(String avatarLocation) {
		this.avatarLocation = avatarLocation;
		createFolderIfNeeded(avatarLocation);
	}
	
	public void setProjectImgLocation(String projectImgLocation) {
		this.projectImgLocation = projectImgLocation;
		createFolderIfNeeded(projectImgLocation);
	}
	
	public void setProjectTmpImgLocation(String projectTmpImgLocation) {
		this.projectTmpImgLocation = projectTmpImgLocation;
		createFolderIfNeeded(projectTmpImgLocation);
	}

}
