package com.jso.deco.api.service.request;

import java.util.List;

import javax.ws.rs.FormParam;

public class ProjectIdeaCreationRequest {
	@FormParam("description")
	private String description;
	@FormParam("images[]")
	private List<String> images;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
