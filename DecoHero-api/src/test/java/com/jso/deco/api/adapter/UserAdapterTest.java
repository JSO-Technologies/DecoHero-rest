package com.jso.deco.api.adapter;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserResgisterRequest;
import com.jso.deco.api.service.response.UserLoginResponse;


public class UserAdapterTest {
	private final UserAdapter adapter = new UserAdapter();

	@Test
	public void userRequestToDBUser_should_adapt_all_request_fields() throws Exception {
		//given
		UserResgisterRequest userRequest = new UserResgisterRequest();
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
	public void dbUserToUserResponse_should_adapt_all_fields() throws Exception {
		//given
		DBUser dbUser = new DBUser();
		dbUser.setId("1234");
		dbUser.setUsername("jsomsanith");
		dbUser.setEmail("jsomsanith@mail.com");
		dbUser.setFirstname("Jimmy");
		dbUser.setLastname("Somsanith");
		dbUser.setBirthdate(new Date());
		
		//when
		UserLoginResponse userResponse = adapter.dbUserToUserResponse(dbUser);
		
		//then
		assertThat(userResponse.getUsername()).isEqualTo(dbUser.getUsername());
		assertThat(userResponse.getEmail()).isEqualTo(dbUser.getEmail());
		assertThat(userResponse.getFirstname()).isEqualTo(dbUser.getFirstname());
		assertThat(userResponse.getLastname()).isEqualTo(dbUser.getLastname());
		assertThat(userResponse.getBirthDate()).isEqualTo(dbUser.getBirthdate().getTime());
	}
}
