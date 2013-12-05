package com.jso.deco.controller;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.User;
import com.jso.deco.controller.adapter.UserAdapter;
import com.jso.deco.data.user.UserDataService;

public class UserController {
    private UserDataService userDataService;
    
    /**
     * Create a new user
     * @param user
     */
    public void createUser(User user) {
    	DBUser dbUser = UserAdapter.userToDBUser(user);
		userDataService.create(dbUser);
		user.setId(dbUser.getId());
    }

    /**
     * Test if a username already exists
     * @param user
     * @return
     */
	public boolean usernameExists(User user) {
		return userDataService.exists(user.getUsername());
	}
	
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}
}
