package com.jso.deco.data.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class DBFriend extends TimeControleDocument {
	@Id
	private String id;
	private String username;
	private String avatar;
	private boolean professionnal;
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isProfessionnal() {
		return professionnal;
	}
	public void setProfessionnal(boolean professionnal) {
		this.professionnal = professionnal;
	}
}	
