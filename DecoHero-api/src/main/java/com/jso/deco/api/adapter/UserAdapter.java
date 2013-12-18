package com.jso.deco.api.adapter;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.api.service.response.UserLoginResponse;

public class UserAdapter {
	
	public DBUser userRequestToDBUser(UserResgisterRequest userRequest) {
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
		response.setUsername(dbUser.getUsername());
		response.setEmail(dbUser.getEmail());
		response.setFirstname(dbUser.getFirstname());
		response.setLastname(dbUser.getLastname());
		response.setBirthDate(dbUser.getBirthdate().getTime());
		return response;
	}
}
