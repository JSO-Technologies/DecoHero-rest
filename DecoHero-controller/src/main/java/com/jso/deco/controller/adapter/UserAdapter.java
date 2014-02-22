package com.jso.deco.controller.adapter;

import com.jso.deco.api.controller.UserInfosResponse;
import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.service.request.UserInfosRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.data.api.DBUser;
import com.jso.deco.data.api.DBUserInfos;

public class UserAdapter {
	
	public DBUser userRequestToDBUser(UserRegisterRequest userRequest) {
		final DBUser dbUser = new DBUser();
		dbUser.setUsername(userRequest.getUsername());
		dbUser.setEmail(userRequest.getEmail());
		dbUser.setFirstname(userRequest.getFirstname());
		dbUser.setLastname(userRequest.getLastname());
		dbUser.setPassword(userRequest.getPassword());
		dbUser.setBirthdate(userRequest.getBirthdate());
		
		return dbUser;
	}
	
	public UserLoginResponse dbUserToUserLoginResponse(DBUser dbUser) {
		final UserLoginResponse response = new UserLoginResponse();
		response.setId(dbUser.getId());
		response.setUsername(dbUser.getUsername());
		response.setEmail(dbUser.getEmail());
		response.setFirstname(dbUser.getFirstname());
		response.setLastname(dbUser.getLastname());
		response.setBirthdate(dbUser.getBirthdate().getTime());
		return response;
	}

	public UserInfosResponse dbUserInfosToUserInfosResponse(final DBUserInfos dbUserInfos, long nbProjects, long nbAchievements, final boolean wholeInfos) {
		final UserInfosResponse response = new UserInfosResponse();
		
		fillPublicFields(response, dbUserInfos);
		response.setNbProjects(nbProjects);
		response.setNbAchievements(nbAchievements);
		if(wholeInfos) {
			fillPrivateFields(response, dbUserInfos);
		}

		return response;
	}

	private void fillPrivateFields(UserInfosResponse response, DBUserInfos dbUserInfos) {
		response.setEmail(dbUserInfos.getEmail());
		response.setFirstname(dbUserInfos.getFirstname());
		response.setLastname(dbUserInfos.getLastname());
		
		response.setRelationship(dbUserInfos.getRelationship());
		response.setChildren(dbUserInfos.getChildren());
		
		response.setAddress(dbUserInfos.getAddress());
		response.setZipcode(dbUserInfos.getZipcode());
		response.setCity(dbUserInfos.getCity());
		response.setPhone(dbUserInfos.getPhone());
		
		response.setJob(dbUserInfos.getJob());
		response.setJob_field(dbUserInfos.getJob_field());
		
		response.setFavorite_color(dbUserInfos.getFavorite_color());
		response.setHouse_type(dbUserInfos.getHouse_type());
	}

	private void fillPublicFields(UserInfosResponse response, DBUserInfos dbUserInfos) {
		response.setId(dbUserInfos.getId());
		response.setUsername(dbUserInfos.getUsername());
		response.setBirthdate(dbUserInfos.getBirthdate().getTime());
		response.setProfessional(dbUserInfos.isProfessionnal());
		response.setGender(dbUserInfos.getGender());
		response.setStyle(dbUserInfos.getStyle());
		response.setAvatar(dbUserInfos.getAvatar());
	}

	public void copyUserInfosFromRequest(DBUserInfos dbUserInfos, UserInfosRequest request) {
		dbUserInfos.setFirstname(request.getFirstname());
		dbUserInfos.setLastname(request.getLastname());
		dbUserInfos.setGender(request.getGender());
		dbUserInfos.setBirthdate(request.getBirthdate());
		
		dbUserInfos.setRelationship(request.getRelationship());
		dbUserInfos.setChildren(request.getChildren());
		dbUserInfos.setZipcode(request.getZipcode());
		dbUserInfos.setAddress(request.getAddress());
		dbUserInfos.setCity(request.getCity());
		dbUserInfos.setPhone(request.getPhone());
		
		dbUserInfos.setJob(request.getJob());
		dbUserInfos.setJob_field(request.getJob_field());
		
		dbUserInfos.setHouse_type(request.getHouse_type());
		dbUserInfos.setFavorite_color(request.getFavorite_color());
		dbUserInfos.setStyle(request.getStyle());
	}

}
