package com.jso.deco.api.adapter;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.api.service.response.UserLoginResponse;

public class UserAdapter {
	
	public DBUser userRequestToDBUser(UserResgisterRequest userRequest) {
		DBUser dbUser = new DBUser();
		dbUser.setUsername(userRequest.getUsername());
		dbUser.setEmail(userRequest.getEmail());
		dbUser.setFirstName(userRequest.getFirstName());
		dbUser.setLastName(userRequest.getLastName());
		dbUser.setPassword(userRequest.getPassword());
		dbUser.setBirthDate(userRequest.getBirthDate());
		
		return dbUser;
	}
	
	public UserLoginResponse dbUserToUserResponse(DBUser dbUser) {
		UserLoginResponse response = new UserLoginResponse();
		response.setUsername(dbUser.getUsername());
		response.setEmail(dbUser.getEmail());
		response.setFirstName(dbUser.getFirstName());
		response.setLastName(dbUser.getLastName());
		response.setBirthDate(dbUser.getBirthDate().getTime());
		return response;
	}
}
