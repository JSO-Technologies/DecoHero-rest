package com.jso.deco.api.controller;

public class Author {
	private String id;
	private String username;
	private boolean professional;
	private String avatar;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isProfessional() {
		return professional;
	}
	
	public void setProfessional(boolean professional) {
		this.professional = professional;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
