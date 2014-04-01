package com.jso.deco.api.controller;

import java.util.Date;

public class FriendResponse {
	private String id;
	private String avatar;
	private String username;
	private boolean professional;
	private Date creationDate;
	
	private long nbProjects;
	private long nbAchievements;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	
	public long getNbProjects() {
		return nbProjects;
	}
	
	public void setNbProjects(long nbProjects) {
		this.nbProjects = nbProjects;
	}
	
	public long getNbAchievements() {
		return nbAchievements;
	}

	public void setNbAchievements(long nbAchievements) {
		this.nbAchievements = nbAchievements;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
