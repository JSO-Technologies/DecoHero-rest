package com.jso.deco.api.controller;

import com.jso.deco.api.common.Gender;
import com.jso.deco.api.common.Style;

public class UserPublicInfosResponse {
	private String id;
	private String username;
	
	private boolean professional;
	private Gender gender;
	
	private String favoriteColor;
	private Style style;
	
	private String avatar;
	
	private long nbProjects;
	private long nbAchievements;
	
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getFavoriteColor() {
		return favoriteColor;
	}
	public void setFavorite_color(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	
}
