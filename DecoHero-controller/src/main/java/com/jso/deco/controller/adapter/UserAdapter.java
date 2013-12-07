package com.jso.deco.controller.adapter;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserResgisterRequest;

public class UserAdapter {
	
	public DBUser userToDBUser(UserResgisterRequest userRequest) {
		DBUser dbUser = new DBUser();
		dbUser.setUsername(userRequest.getUsername());
		dbUser.setEmail(userRequest.getEmail());
		dbUser.setFirstName(userRequest.getFirstName());
		dbUser.setLastName(userRequest.getLastName());
		dbUser.setPassword(userRequest.getPassword());
		dbUser.setBirthDate(userRequest.getBirthDate());
		
		return dbUser;
	}
}
