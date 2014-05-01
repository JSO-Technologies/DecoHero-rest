package com.jso.deco.api.service.request;

import java.util.Date;

import javax.ws.rs.FormParam;

import com.jso.deco.api.common.Gender;
import com.jso.deco.api.common.HouseType;
import com.jso.deco.api.common.Job;
import com.jso.deco.api.common.JobField;
import com.jso.deco.api.common.Relationship;
import com.jso.deco.api.common.Style;

public class UserInfosRequest {
	@FormParam("firstname")
	private String firstname;
	@FormParam("lastname")
	private String lastname;
	@FormParam("gender")
	private Gender gender;
	@FormParam("birthdate")
	private Long birthdateTimestamp;
	private Date birthdate;
	
	@FormParam("relationship")
	private Relationship relationship; 
	@FormParam("children")
	private Integer children;

	@FormParam("address")
	private String address;
	@FormParam("zipcode")
	private Integer zipcode;
	@FormParam("city")
	private String city;
	@FormParam("phone")
	private String phone;
	
	@FormParam("job")
	private Job job;
	@FormParam("jobField")
	private JobField jobField;
	
	@FormParam("houseType")
	private HouseType houseType;
	@FormParam("favoriteColor")
	private String favoriteColor;
	@FormParam("style")
	private Style style;

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
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Long getBirthdateTimestamp() {
		return birthdateTimestamp;
	}

	public void setBirthdateTimestamp(Long birthdateTimestamp) {
		this.birthdateTimestamp = birthdateTimestamp;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Relationship getRelationship() {
		return relationship;
	}
	
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
	
	public Integer getChildren() {
		return children;
	}
	
	public void setChildren(Integer children) {
		this.children = children;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Job getJob() {
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public JobField getJobField() {
		return jobField;
	}
	
	public void setJobField(JobField jobField) {
		this.jobField = jobField;
	}
	
	public HouseType getHouseType() {
		return houseType;
	}
	
	public void setHouse_type(HouseType houseType) {
		this.houseType = houseType;
	}
	
	public String getFavoriteColor() {
		return favoriteColor;
	}
	
	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}
	
	public Style getStyle() {
		return style;
	}
	
	public void setStyle(Style style) {
		this.style = style;
	}

}
