package com.jso.deco.data.api;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.jso.deco.api.common.Gender;
import com.jso.deco.api.common.HouseType;
import com.jso.deco.api.common.Job;
import com.jso.deco.api.common.JobField;
import com.jso.deco.api.common.Relationship;
import com.jso.deco.api.common.Style;

@Document(collection = "users")
public class DBUserInfos extends DBUser {
	private boolean professionnal;
	private Gender gender;
	private Relationship relationship; 
	private Integer children;

	private String address;
	private Integer zipcode;
	private String city;
	private String phone;
	
	private Job job;
	private JobField job_field;
	
	private String favorite_color;
	private HouseType house_type;
	private Style style;
	
	private List<String> friends = newArrayList();
	
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
	public JobField getJob_field() {
		return job_field;
	}
	public void setJob_field(JobField job_field) {
		this.job_field = job_field;
	}
	public String getFavorite_color() {
		return favorite_color;
	}
	public void setFavorite_color(String favorite_color) {
		this.favorite_color = favorite_color;
	}
	public HouseType getHouse_type() {
		return house_type;
	}
	public void setHouse_type(HouseType house_type) {
		this.house_type = house_type;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	public List<String> getFriends() {
		return friends;
	}
}	
