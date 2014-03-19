package com.jso.deco.controller.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;


public class ImageService {
	private static final String ENCODING_PREFIX = "base64,";	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	private final Base64 base64 = new Base64();
	private String avatarLocation;
	private String projectImgLocation;
	
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
	 * Generate unique ids for each image
	 * @param images
	 * @return
	 */
	public Map<String, String> generateIds(List<String> images) {
		final Map<String, String> imagesWithId = new LinkedHashMap<>(images.size());
		for(String image : images) {
			imagesWithId.put(UUID.randomUUID().toString(), image);
		}
		return imagesWithId;
	}
	
	/**
	 * Get project image byte array
	 * @param projectId
	 * @param imageId
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
	 * Get project idea image byte array
	 * @param projectId
	 * @param ideaId
	 * @param imageId
	 * @return
	 */
	public byte[] getProjectIdeaImage(String projectId, String ideaId, String imageId) {
		try {
			File imageFile = new File(projectImgLocation + projectId + "/" + ideaId + "/" + imageId);
			return Files.readAllBytes(imageFile.toPath());
		} catch (IOException e) {
			LOGGER.error("Error while reading project idea image " + projectImgLocation + projectId + "/" + ideaId + "/" + imageId + " : " + e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Save project images from dataUrls
	 * @param imgDataUrls
	 * @return
	 * @throws DHServiceException 
	 */
	public void saveProjectImg(String projectId, Map<String, String> imgDataUrls) throws DHServiceException {
		String projectFolder = projectImgLocation + "/" + projectId + "/";
		saveImages(projectFolder, imgDataUrls);
	}
	
	/**
	 * Save project idea images from dataUrls
	 * @param projectId
	 * @param ideaId 
	 * @param images
	 * @return
	 * @throws DHServiceException 
	 */
	public void saveProjectIdeaImg(String projectId, String ideaId, Map<String, String> images) throws DHServiceException {
		String projectFolder = projectImgLocation + "/" + projectId + "/" + ideaId;
		saveImages(projectFolder, images);
	}
	
	/**
	 * Save all images (Map<imgId, dataUrl>) in specified folder
	 * @param folderPath
	 * @param images
	 * @throws DHServiceException
	 */
	private void saveImages(String folderPath, Map<String, String> images) throws DHServiceException {
		createFolderIfNeeded(folderPath);
		
		for(Entry<String, String> imageToSave : images.entrySet()) {
			final String dataUrl = imageToSave.getValue();
			int contentStartIndex = dataUrl .indexOf(ENCODING_PREFIX) + ENCODING_PREFIX.length();
			byte[] imageData = base64.decode(dataUrl.substring(contentStartIndex).getBytes());			
			saveToFile(new ByteArrayInputStream(imageData), folderPath + "/" + imageToSave.getKey());
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

}
