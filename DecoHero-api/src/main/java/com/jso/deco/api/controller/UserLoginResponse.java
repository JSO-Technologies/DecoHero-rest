package com.jso.deco.api.controller;


public class UserLoginResponse {
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private Long birthdate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Long birthdate) {
		this.birthdate = birthdate;
	}

	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}	
