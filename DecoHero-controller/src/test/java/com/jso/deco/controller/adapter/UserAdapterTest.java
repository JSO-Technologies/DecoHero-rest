package com.jso.deco.controller.adapter;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.controller.UserInfosResponse;
import com.jso.deco.api.controller.UserLoginResponse;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.controller.utils.DefaultTestData;
import com.jso.deco.data.api.DBUser;
import com.jso.deco.data.api.DBUserInfos;


public class UserAdapterTest {
	private final UserAdapter adapter = new UserAdapter();

	@Test
	public void userRequestToDBUser_should_adapt_all_request_fields() throws Exception {
		//given
		UserRegisterRequest userRequest = new UserRegisterRequest();
		userRequest.setUsername("username");
		userRequest.setEmail("email");
		userRequest.setFirstname("firstName");
		userRequest.setLastname("lastName");
		userRequest.setPassword("password");
		userRequest.setBirthdate(new Date());

		//when
		DBUser dbUser = adapter.userRequestToDBUser(userRequest);

		//then
		assertThat(dbUser.getUsername()).isEqualTo(userRequest.getUsername());
		assertThat(dbUser.getEmail()).isEqualTo(userRequest.getEmail());
		assertThat(dbUser.getFirstname()).isEqualTo(userRequest.getFirstname());
		assertThat(dbUser.getLastname()).isEqualTo(userRequest.getLastname());
		assertThat(dbUser.getPassword()).isEqualTo(userRequest.getPassword());
		assertThat(dbUser.getBirthdate()).isEqualTo(userRequest.getBirthdate());
	}
	
	@Test
	public void dbUserToUserLoginResponse_should_adapt_all_fields() throws Exception {
		//given
		DBUser dbUser = new DBUser();
		dbUser.setId("1234");
		dbUser.setUsername("jsomsanith");
		dbUser.setEmail("jsomsanith@mail.com");
		dbUser.setFirstname("Jimmy");
		dbUser.setLastname("Somsanith");
		dbUser.setBirthdate(new Date());
		dbUser.setAvatar("1234");
		
		//when
		UserLoginResponse userResponse = adapter.dbUserToUserLoginResponse(dbUser);
		
		//then
		assertThat(userResponse.getId()).isEqualTo(dbUser.getId());
		assertThat(userResponse.getUsername()).isEqualTo(dbUser.getUsername());
		assertThat(userResponse.getEmail()).isEqualTo(dbUser.getEmail());
		assertThat(userResponse.getFirstname()).isEqualTo(dbUser.getFirstname());
		assertThat(userResponse.getLastname()).isEqualTo(dbUser.getLastname());
		assertThat(userResponse.getBirthdate()).isEqualTo(dbUser.getBirthdate().getTime());
		assertThat(userResponse.getAvatar()).isEqualTo(dbUser.getAvatar());
	}
	
	@Test
	public void dbUserInfosToUserInfosResponse_should_adapt_all_fields() {
		//given
		DBUserInfos dbUserInfos = DefaultTestData.getDefaultDBUserInfos();
		
		//when
		UserInfosResponse response = adapter.dbUserInfosToUserInfosResponse(dbUserInfos, 2, 12, true);
		
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

		assertThat(response.getAvatar()).isEqualTo(dbUserInfos.getAvatar());

		assertThat(response.getNbProjects()).isEqualTo(2);
		assertThat(response.getNbAchievements()).isEqualTo(12);
	}

	@Test
	public void dbUserInfosToUserInfosResponse_should_adapt_only_public_fields() {
		//given
		DBUserInfos dbUserInfos = DefaultTestData.getDefaultDBUserInfos();
		
		//when
		UserInfosResponse response = adapter.dbUserInfosToUserInfosResponse(dbUserInfos, 1, 8, false);
		
		//then
		assertThat(response.getId()).isEqualTo(dbUserInfos.getId());
		assertThat(response.getUsername()).isEqualTo(dbUserInfos.getUsername());
		assertThat(response.getBirthdate()).isEqualTo(dbUserInfos.getBirthdate().getTime());
		assertThat(response.isProfessional()).isEqualTo(dbUserInfos.isProfessionnal());
		assertThat(response.getGender()).isEqualTo(dbUserInfos.getGender());
		assertThat(response.getStyle()).isEqualTo(dbUserInfos.getStyle());
		assertThat(response.getAvatar()).isEqualTo(dbUserInfos.getAvatar());
		
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
		
		assertThat(response.getNbProjects()).isEqualTo(1);
		assertThat(response.getNbAchievements()).isEqualTo(8);
	}
	
}
