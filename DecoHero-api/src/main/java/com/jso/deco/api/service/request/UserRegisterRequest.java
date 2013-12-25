package com.jso.deco.api.service.request;

import java.util.Date;

import javax.ws.rs.FormParam;

public class UserRegisterRequest {
	@FormParam("username")
	private String username;
	@FormParam("email")
	private String email;
	@FormParam("firstname")
	private String firstname;
	@FormParam("lastname")
	private String lastname;
	@FormParam("birthdate")
	private Long birthdateTimestamp;
	private Date birthdate;
	@FormParam("password")
	private String password;

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
	
	public void setBirthdateTimestamp(Long birthdateTimestamp) {
		this.birthdateTimestamp = birthdateTimestamp;
	}
	
	public Long getBirthdateTimestamp() {
		return birthdateTimestamp;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}	
