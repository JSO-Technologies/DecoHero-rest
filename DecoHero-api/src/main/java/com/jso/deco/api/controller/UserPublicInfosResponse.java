package com.jso.deco.api.controller;

import com.jso.deco.api.common.Gender;
import com.jso.deco.api.common.Style;

public class UserPublicInfosResponse {
	private String id;
	private String username;
	
	private boolean professionnal;
	private Gender gender;
	
	private String favorite_color;
	private Style style;
	
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
	public boolean isProfessionnal() {
		return professionnal;
	}
	public void setProfessionnal(boolean professionnal) {
		this.professionnal = professionnal;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getFavorite_color() {
		return favorite_color;
	}
	public void setFavorite_color(String favorite_color) {
		this.favorite_color = favorite_color;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	
}
