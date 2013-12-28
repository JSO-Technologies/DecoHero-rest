package com.jso.deco.controller.utils;

import java.util.Date;

import com.jso.deco.api.common.Gender;
import com.jso.deco.api.common.HouseType;
import com.jso.deco.api.common.Job;
import com.jso.deco.api.common.JobField;
import com.jso.deco.api.common.Relationship;
import com.jso.deco.api.common.Style;
import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.database.DBUserInfos;

public class DefaultTestData {
	public static DBUser getDefaultDBUser() {
		DBUser user = new DBUser();
		user.setId("12345");
		user.setUsername("jsomsanith");
		user.setFirstname("Jimmy");
		user.setLastname("Somsanith");
		user.setEmail("jimmy.somsanith@gmail.com");
		user.setPassword("kdfgqsjdhfksdf");
		user.setBirthdate(new Date());
		return user;
	}
	
	public static DBUserInfos getDefaultDBUserInfos() {
		DBUserInfos dbUserInfos = new DBUserInfos();
		dbUserInfos.setId("1234");
		dbUserInfos.setUsername("jsomsanith");
		dbUserInfos.setBirthdate(new Date());
		dbUserInfos.setProfessionnal(true);
		dbUserInfos.setGender(Gender.M);
		dbUserInfos.setStyle(Style.M);
		
		dbUserInfos.setEmail("jsomsanith@mail.com");
		dbUserInfos.setFirstname("Jimmy");
		dbUserInfos.setLastname("Somsanith");
		
		dbUserInfos.setRelationship(Relationship.M);
		dbUserInfos.setChildren(0);
		
		dbUserInfos.setAddress("4 av Louis Luc");
		dbUserInfos.setZipcode(94600);
		dbUserInfos.setCity("Choisy Le Roi");
		dbUserInfos.setPhone("0677406253");
		
		dbUserInfos.setJob(Job.INGR);
		dbUserInfos.setJob_field(JobField.IT);
		
		dbUserInfos.setFavorite_color("#000000");
		dbUserInfos.setHouse_type(HouseType.AO);
		
		return dbUserInfos;
	}
}
