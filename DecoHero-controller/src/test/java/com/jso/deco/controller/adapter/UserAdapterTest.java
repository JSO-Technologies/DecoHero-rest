package com.jso.deco.controller.adapter;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.database.DBUser;
import com.jso.deco.api.service.request.UserResgisterRequest;

public class UserAdapterTest {
	private final UserAdapter adapter = new UserAdapter();
	
	@Test
	public void userToDBUser_should_adapt_all_request_fields() throws Exception {
		//given
		UserResgisterRequest userRequest = new UserResgisterRequest();
		userRequest.setUsername("username");
		userRequest.setEmail("email");
		userRequest.setFirstName("firstName");
		userRequest.setLastName("lastName");
		userRequest.setPassword("password");
		userRequest.setBirthDate(new Date());
		
		//when
		DBUser dbUser = adapter.userToDBUser(userRequest);
		
		//then
		assertThat(dbUser.getUsername()).isEqualTo(userRequest.getUsername());
		assertThat(dbUser.getEmail()).isEqualTo(userRequest.getEmail());
		assertThat(dbUser.getFirstName()).isEqualTo(userRequest.getFirstName());
		assertThat(dbUser.getLastName()).isEqualTo(userRequest.getLastName());
		assertThat(dbUser.getPassword()).isEqualTo(userRequest.getPassword());
		assertThat(dbUser.getBirthDate()).isEqualTo(userRequest.getBirthDate());
	}
}
