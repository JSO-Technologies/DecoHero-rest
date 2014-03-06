package com.jso.deco.api.controller;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;


public class ProjectIdeaResponse {
	private String id;
	private String description;
	private List<String> images = Lists.newArrayList();
	private Author author;
	private Date creationDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
