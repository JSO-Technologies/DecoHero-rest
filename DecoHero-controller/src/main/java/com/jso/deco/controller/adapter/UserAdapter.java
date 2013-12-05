package com.jso.deco.controller.adapter;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.User;

public class UserAdapter {
	public static DBUser userToDBUser(User user) {
		DBUser dbUser = new DBUser();
		dbUser.setId(user.getId());
		dbUser.setUsername(user.getUsername());
		dbUser.setEmail(user.getEmail());
		dbUser.setFirstName(user.getFirstName());
		dbUser.setLastName(user.getLastName());
		dbUser.setPassword(user.getPassword());
		dbUser.setBirthDate(user.getBirthDate());
		
		return dbUser;
	}
}
