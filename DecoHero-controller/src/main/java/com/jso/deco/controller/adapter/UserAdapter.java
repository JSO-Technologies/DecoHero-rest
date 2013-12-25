package com.jso.deco.controller.adapter;

import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserRegisterRequest;

public class UserAdapter {
	
	public DBUser userRequestToDBUser(UserRegisterRequest userRequest) {
		DBUser dbUser = new DBUser();
		dbUser.setUsername(userRequest.getUsername());
		dbUser.setEmail(userRequest.getEmail());
		dbUser.setFirstname(userRequest.getFirstname());
		dbUser.setLastname(userRequest.getLastname());
		dbUser.setPassword(userRequest.getPassword());
		dbUser.setBirthdate(userRequest.getBirthdate());
		
		return dbUser;
	}
	
	public UserLoginResponse dbUserToUserResponse(DBUser dbUser) {
		UserLoginResponse response = new UserLoginResponse();
		response.setId(dbUser.getId());
		response.setUsername(dbUser.getUsername());
		response.setEmail(dbUser.getEmail());
		response.setFirstname(dbUser.getFirstname());
		response.setLastname(dbUser.getLastname());
		response.setBirthDate(dbUser.getBirthdate().getTime());
		return response;
	}

}
