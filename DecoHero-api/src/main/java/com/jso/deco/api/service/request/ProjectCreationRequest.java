package com.jso.deco.api.service.request;

import java.util.List;

import javax.ws.rs.FormParam;

import com.jso.deco.api.common.Category;
import com.jso.deco.api.common.Room;

public class ProjectCreationRequest {
	@FormParam("title")
	private String title;
	@FormParam("description")
	private String description;
	@FormParam("images[]")
	private List<String> images;
	@FormParam("category")
	private Category category;
	@FormParam("room")
	private Room room;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
