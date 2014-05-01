package com.jso.deco.api.controller;

import com.jso.deco.api.common.HouseType;
import com.jso.deco.api.common.Job;
import com.jso.deco.api.common.JobField;
import com.jso.deco.api.common.Relationship;

public class UserInfosResponse extends UserPublicInfosResponse {
	private String email;
	private String firstname;
	private String lastname;
	private Long birthdate;
	
	private Relationship relationship; 
	private Integer children;

	private String address;
	private Integer zipcode;
	private String city;
	private String phone;
	
	private Job job;
	private JobField jobField;
	
	private HouseType houseType;

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

	public void setJob_field(JobField jobField) {
		this.jobField = jobField;
	}

	public HouseType getHouseType() {
		return houseType;
	}

	public void setHouse_type(HouseType houseType) {
		this.houseType = houseType;
	}
	
}
