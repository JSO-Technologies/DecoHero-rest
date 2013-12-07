package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.REGISTRATION_EMAIL_ALREADY_EXISTS;
import static com.jso.deco.api.exception.DHMessageCode.REGISTRATION_USERNAME_ALREADY_EXISTS;
import static com.jso.deco.data.user.UserDataService.EMAIL;
import static com.jso.deco.data.user.UserDataService.USERNAME;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.controller.adapter.UserAdapter;
import com.jso.deco.data.user.UserDataService;

public class UserController {
	private UserAdapter adapter;
    private UserDataService userDataService;
    
    /**
     * Create a new user
     * @param user
     * @return 
     * @throws DHServiceException 
     */
    public String createUser(final UserResgisterRequest user) throws DHServiceException {
    	if(userDataService.exists(USERNAME, user.getUsername())) {
			throw new DHServiceException(REGISTRATION_USERNAME_ALREADY_EXISTS, "username");
		}
    	if(userDataService.exists(EMAIL, user.getEmail())) {
			throw new DHServiceException(REGISTRATION_EMAIL_ALREADY_EXISTS, "email");
		}
    	
    	DBUser dbUser = adapter.userToDBUser(user);
		userDataService.create(dbUser);
		return dbUser.getId();
    }
	
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}
	
	public void setAdapter(UserAdapter adapter) {
		this.adapter = adapter;
	}
}
