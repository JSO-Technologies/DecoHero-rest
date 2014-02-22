package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.USER_ALREADY_EXISTS;
import static com.jso.deco.data.service.UserDataService.EMAIL;
import static com.jso.deco.data.service.UserDataService.USERNAME;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jso.deco.api.controller.UserInfosResponse;
import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.controller.adapter.UserAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.controller.utils.DefaultTestData;
import com.jso.deco.data.api.DBUser;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.UserDataService;


public class UserControllerTest {
	private final UserController controller = new UserController();
	private final UserDataService userDataService = mock(UserDataService.class);
	private final ProjectDataService projectDataService = mock(ProjectDataService.class);
	private final ImageService imageService = mock(ImageService.class);

	@Before
	public void init() {
		controller.setUserDataService(userDataService);
		controller.setAdapter(new UserAdapter());
		controller.setImageService(imageService);
		controller.setProjectDataService(projectDataService);
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
		assertThat(response.getBirthdate()).isEqualTo(user.getBirthdate().getTime());
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
		DBUser dbUser = DefaultTestData.getDefaultDBUser();

		when(userDataService.find("email", "password")).thenReturn(dbUser);

		//when
		UserLoginResponse response = controller.login(request);

		//then
		assertThat(response.getId()).isEqualTo(dbUser.getId());
		assertThat(response.getUsername()).isEqualTo(dbUser.getUsername());
		assertThat(response.getFirstname()).isEqualTo(dbUser.getFirstname());
		assertThat(response.getLastname()).isEqualTo(dbUser.getLastname());
		assertThat(response.getEmail()).isEqualTo(dbUser.getEmail());
		assertThat(response.getBirthdate()).isEqualTo(dbUser.getBirthdate().getTime());
	}

	@Test
	public void getUserInfos_should_get_all_infos() throws Exception {
		//given
		DBUserInfos dbUserInfos = DefaultTestData.getDefaultDBUserInfos();
		when(userDataService.findInfosById("1234")).thenReturn(dbUserInfos);
		when(projectDataService.countUserProjects("1234")).thenReturn(6L);

		//when
		UserInfosResponse response = controller.getUserInfos("1234", true);

		//then
		assertThat(response.getId()).isEqualTo(dbUserInfos.getId());
		assertThat(response.getUsername()).isEqualTo(dbUserInfos.getUsername());
		assertThat(response.getBirthdate()).isEqualTo(dbUserInfos.getBirthdate().getTime());
		assertThat(response.isProfessional()).isEqualTo(dbUserInfos.isProfessionnal());
		assertThat(response.getGender()).isEqualTo(dbUserInfos.getGender());
		assertThat(response.getStyle()).isEqualTo(dbUserInfos.getStyle());

		assertThat(response.getEmail()).isEqualTo(dbUserInfos.getEmail());
		assertThat(response.getFirstname()).isEqualTo(dbUserInfos.getFirstname());
		assertThat(response.getLastname()).isEqualTo(dbUserInfos.getLastname());

		assertThat(response.getRelationship()).isEqualTo(dbUserInfos.getRelationship());
		assertThat(response.getChildren()).isEqualTo(dbUserInfos.getChildren());

		assertThat(response.getAddress()).isEqualTo(dbUserInfos.getAddress());
		assertThat(response.getZipcode()).isEqualTo(dbUserInfos.getZipcode());
		assertThat(response.getCity()).isEqualTo(dbUserInfos.getCity());
		assertThat(response.getPhone()).isEqualTo(dbUserInfos.getPhone());

		assertThat(response.getJob()).isEqualTo(dbUserInfos.getJob());
		assertThat(response.getJob_field()).isEqualTo(dbUserInfos.getJob_field());

		assertThat(response.getFavorite_color()).isEqualTo(dbUserInfos.getFavorite_color());
		assertThat(response.getHouse_type()).isEqualTo(dbUserInfos.getHouse_type());

		assertThat(response.getNbProjects()).isEqualTo(6L);
	}

	@Test
	public void getUserInfos_should_get_only_public_infos() throws Exception {
		//given
		DBUserInfos dbUserInfos = DefaultTestData.getDefaultDBUserInfos();
		when(userDataService.findInfosById("1234")).thenReturn(dbUserInfos);
		when(projectDataService.countUserProjects("1234")).thenReturn(8L);

		//when
		UserInfosResponse response = controller.getUserInfos("1234", false);

		//then
		assertThat(response.getId()).isEqualTo(dbUserInfos.getId());
		assertThat(response.getUsername()).isEqualTo(dbUserInfos.getUsername());
		assertThat(response.getBirthdate()).isEqualTo(dbUserInfos.getBirthdate().getTime());
		assertThat(response.isProfessional()).isEqualTo(dbUserInfos.isProfessionnal());
		assertThat(response.getGender()).isEqualTo(dbUserInfos.getGender());
		assertThat(response.getStyle()).isEqualTo(dbUserInfos.getStyle());

		assertThat(response.getEmail()).isNull();
		assertThat(response.getFirstname()).isNull();
		assertThat(response.getLastname()).isNull();

		assertThat(response.getRelationship()).isNull();
		assertThat(response.getChildren()).isNull();

		assertThat(response.getAddress()).isNull();
		assertThat(response.getZipcode()).isNull();
		assertThat(response.getCity()).isNull();
		assertThat(response.getPhone()).isNull();

		assertThat(response.getJob()).isNull();
		assertThat(response.getJob_field()).isNull();

		assertThat(response.getFavorite_color()).isNull();
		assertThat(response.getHouse_type()).isNull();
		
		assertThat(response.getNbProjects()).isEqualTo(8L);
	}

	@Test
	public void getUserInfos_should_throw_exception_if_user_not_found() throws Exception {
		//given
		when(userDataService.findInfosById("1234")).thenReturn(null);

		//when
		try {
			controller.getUserInfos("1234", true);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.USER_DOESNT_EXIST);
		}
	}
	
	@Test
	public void updateAvatar_should_update_avatar_and_update_user_infos_avatar_field() throws DHServiceException {
		//given
		String userId = "123";
		String imageEncodedId = userId;
		String avatarDataUrl = "data:image/png;base64,erhAAZJEOEankzelajzeARHfzgfe";
		
		doNothing().when(imageService).saveAvatar(userId, avatarDataUrl);
		when(userDataService.userAvatarEmpty(userId)).thenReturn(true);
		doNothing().when(userDataService).updateAvatar(userId, imageEncodedId);
		
		//when
		controller.updateAvatar(userId, avatarDataUrl);
		
		//then
		verify(imageService, times(1)).saveAvatar(userId, avatarDataUrl);
		verify(userDataService, times(1)).updateAvatar(userId, imageEncodedId);
	}
	
	@Test
	public void updateAvatar_should_update_avatar_but_not_update_user_infos_avatar_field() throws DHServiceException {
		//given
		String userId = "123";
		String imageEncodedId = userId;
		String avatarDataUrl = "data:image/png;base64,erhAAZJEOEankzelajzeARHfzgfe";
		
		doNothing().when(imageService).saveAvatar(userId, avatarDataUrl);
		when(userDataService.userAvatarEmpty(userId)).thenReturn(false);
		doNothing().when(userDataService).updateAvatar(userId, imageEncodedId);
		
		//when
		controller.updateAvatar(userId, avatarDataUrl);
		
		//then
		verify(imageService, times(1)).saveAvatar(userId, avatarDataUrl);
		verify(userDataService, times(0)).updateAvatar(userId, imageEncodedId);
	}
}
