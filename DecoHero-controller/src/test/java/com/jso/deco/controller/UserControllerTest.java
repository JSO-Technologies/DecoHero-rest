package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.REGISTRATION_EMAIL_ALREADY_EXISTS;
import static com.jso.deco.api.exception.DHMessageCode.REGISTRATION_USERNAME_ALREADY_EXISTS;
import static com.jso.deco.data.user.UserDataService.EMAIL;
import static com.jso.deco.data.user.UserDataService.USERNAME;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.data.user.UserDataService;


public class UserControllerTest {
	private final UserController controller = new UserController();
	private UserDataService userDataService = mock(UserDataService.class);
	
	@Before
	public void init() {
		controller.setUserDataService(userDataService);
	}
	
	@Test
	public void createUser_should_throw_exception_if_username_already_exists() {
		//given
		UserResgisterRequest user = new UserResgisterRequest();
		user.setUsername("username");
		user.setEmail("email");
		
		Mockito.when(userDataService.exists(USERNAME, "username")).thenReturn(true);
		
		//when
		try {
			controller.createUser(user);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(REGISTRATION_USERNAME_ALREADY_EXISTS);
			assertThat(e.getDetails()).isEqualTo("username");
		}
	}
	
	@Test
	public void createUser_should_throw_exception_if_email_already_exists() {
		//given
		UserResgisterRequest user = new UserResgisterRequest();
		user.setUsername("username");
		user.setEmail("email");
		
		Mockito.when(userDataService.exists(USERNAME, "username")).thenReturn(false);
		Mockito.when(userDataService.exists(EMAIL, "email")).thenReturn(true);
		
		//when
		try {
			controller.createUser(user);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(REGISTRATION_EMAIL_ALREADY_EXISTS);
			assertThat(e.getDetails()).isEqualTo("email");
		}
	}
	
	@Test
	public void createUser_should_pass() throws DHServiceException {
		//given
		UserResgisterRequest user = new UserResgisterRequest();
		user.setUsername("username");
		user.setEmail("email");
		
		Mockito.when(userDataService.exists(USERNAME, "username")).thenReturn(false);
		Mockito.when(userDataService.exists(EMAIL, "email")).thenReturn(false);
		
		//when
		controller.createUser(user);

		//then
	}
}
