package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.USER_ALREADY_EXISTS;
import static com.jso.deco.api.exception.DHMessageCode.USER_DOESNT_EXIST;
import static com.jso.deco.data.user.UserDataService.EMAIL;
import static com.jso.deco.data.user.UserDataService.USERNAME;

import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
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
    public UserLoginResponse createUser(final UserRegisterRequest user) throws DHServiceException {
    	if(userDataService.exists(USERNAME, user.getUsername())) {
			throw new DHServiceException(USER_ALREADY_EXISTS, "username");
		}
    	if(userDataService.exists(EMAIL, user.getEmail())) {
			throw new DHServiceException(USER_ALREADY_EXISTS, "email");
		}
    	
    	DBUser dbUser = adapter.userRequestToDBUser(user);
		userDataService.create(dbUser);
		UserLoginResponse response = adapter.dbUserToUserResponse(dbUser);
		
		return response;
    }
    
    /**
     * Get user for login
     * @param request
     * @return
     * @throws DHServiceException 
     */
    public UserLoginResponse login(UserLoginRequest request) throws DHServiceException {
		DBUser dbUser = userDataService.find(request.getEmail(), request.getPassword());
		if(dbUser == null) {
			throw new DHServiceException(USER_DOESNT_EXIST, null);
		}
		
		UserLoginResponse response = adapter.dbUserToUserResponse(dbUser);
		
		return response;
    }
	
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}
	
	public void setAdapter(UserAdapter adapter) {
		this.adapter = adapter;
	}
}
