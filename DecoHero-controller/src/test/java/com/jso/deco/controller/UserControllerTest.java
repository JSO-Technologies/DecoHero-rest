package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.USER_ALREADY_EXISTS;
import static com.jso.deco.data.user.UserDataService.EMAIL;
import static com.jso.deco.data.user.UserDataService.USERNAME;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.controller.adapter.UserAdapter;
import com.jso.deco.data.user.UserDataService;


public class UserControllerTest {
	private final UserController controller = new UserController();
	private UserDataService userDataService = mock(UserDataService.class);
	
	@Before
	public void init() {
		controller.setUserDataService(userDataService);
		controller.setAdapter(new UserAdapter());
	}
	
	@Test
	public void createUser_should_throw_exception_if_username_already_exists() {
		//given
		UserRegisterRequest user = new UserRegisterRequest();
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
			assertThat(e.getDhMessage()).isEqualTo(USER_ALREADY_EXISTS);
			assertThat(e.getDetails()).isEqualTo("username");
		}
	}
	
	@Test
	public void createUser_should_throw_exception_if_email_already_exists() {
		//given
		UserRegisterRequest user = new UserRegisterRequest();
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
			assertThat(e.getDhMessage()).isEqualTo(USER_ALREADY_EXISTS);
			assertThat(e.getDetails()).isEqualTo("email");
		}
	}
	
	@Test
	public void createUser_should_pass() throws DHServiceException {
		//given
		UserRegisterRequest user = new UserRegisterRequest();
		user.setUsername("jsomsanith");
		user.setFirstname("Jimmy");
		user.setLastname("Somsanith");
		user.setEmail("jimmy.somsanith@gmail.com");
		user.setPassword("kdfgqsjdhfksdf");
		user.setBirthdate(new Date());
		
		when(userDataService.exists(USERNAME, "username")).thenReturn(false);
		when(userDataService.exists(EMAIL, "email")).thenReturn(false);
		doNothing().when(userDataService).create(Mockito.any(DBUser.class));
		
		//when
		UserLoginResponse response = controller.createUser(user);

		//then
		assertThat(response.getUsername()).isEqualTo(user.getUsername());
		assertThat(response.getFirstname()).isEqualTo(user.getFirstname());
		assertThat(response.getLastname()).isEqualTo(user.getLastname());
		assertThat(response.getEmail()).isEqualTo(user.getEmail());
		assertThat(response.getBirthDate()).isEqualTo(user.getBirthdate().getTime());
	}
	
	@Test
	public void login_should_throw_exception_if_user_not_found() {
		//given
		UserLoginRequest request = new UserLoginRequest();
		request.setEmail("email");
		request.setPassword("password");
		
		when(userDataService.find("email", "password")).thenReturn(null);

		//when
		try {
			controller.login(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.USER_DOESNT_EXIST);
		}
	}
	
	@Test
	public void login_should_pass() throws Exception {
		//given
		UserLoginRequest request = new UserLoginRequest();
		request.setEmail("email");
		request.setPassword("password");
		DBUser dbUser = getDefaultDBUser();
		
		when(userDataService.find("email", "password")).thenReturn(dbUser);
		
		//when
		UserLoginResponse response = controller.login(request);
		
		//then
		assertThat(response.getId()).isEqualTo(dbUser.getId());
		assertThat(response.getUsername()).isEqualTo(dbUser.getUsername());
		assertThat(response.getFirstname()).isEqualTo(dbUser.getFirstname());
		assertThat(response.getLastname()).isEqualTo(dbUser.getLastname());
		assertThat(response.getEmail()).isEqualTo(dbUser.getEmail());
		assertThat(response.getBirthDate()).isEqualTo(dbUser.getBirthdate().getTime());
	}

	private DBUser getDefaultDBUser() {
		DBUser user = new DBUser();
		user.setId("12345");
		user.setUsername("jsomsanith");
		user.setFirstname("Jimmy");
		user.setLastname("Somsanith");
		user.setEmail("jimmy.somsanith@gmail.com");
		user.setPassword("kdfgqsjdhfksdf");
		user.setBirthdate(new Date());
		return user;
	}
	
	
}
