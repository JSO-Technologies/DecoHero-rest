package com.jso.deco.controller.adapter;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.controller.FriendResponse;
import com.jso.deco.data.api.DBFriend;


public class FriendAdapterTest {
	private FriendAdapter adapter = new FriendAdapter();
	
	@Test
	public void dbFriendToFriendResponse_should_adapt_all_fields() {
		// given
		final DBFriend dbFriend = new DBFriend();
		dbFriend.setId("12345");
		dbFriend.setAvatar("avatarId");
		dbFriend.setUsername("username");
		dbFriend.setProfessionnal(true);
		dbFriend.setCreationDate(new Date());
		
		final long nbProjects = 5;
		final long nbAchievements = 3;
		
		// when
		final FriendResponse response = adapter.dbFriendToFriendResponse(dbFriend, nbProjects, nbAchievements);
		
		// then
		assertThat(response.getId()).isEqualTo(dbFriend.getId());
		assertThat(response.getAvatar()).isEqualTo(dbFriend.getAvatar());
		assertThat(response.getUsername()).isEqualTo(dbFriend.getUsername());
		assertThat(response.isProfessional()).isEqualTo(dbFriend.isProfessionnal());
		assertThat(response.getCreationDate()).isEqualTo(dbFriend.getCreationDate());
		assertThat(response.getNbProjects()).isEqualTo(nbProjects);
		assertThat(response.getNbAchievements()).isEqualTo(nbAchievements);
	}
}
