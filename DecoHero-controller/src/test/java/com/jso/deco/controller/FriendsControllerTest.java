package com.jso.deco.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jso.deco.api.controller.FriendResponse;
import com.jso.deco.api.controller.FriendsListResponse;
import com.jso.deco.controller.adapter.FriendAdapter;
import com.jso.deco.data.api.DBFriend;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.UserDataService;


public class FriendsControllerTest {
	private final FriendsController controller = new FriendsController();
	private final UserDataService userDataService = mock(UserDataService.class);
	private final ProjectDataService projectDataService = mock(ProjectDataService.class);
	
	@Before
	public void init() {
		controller.setUserDataService(userDataService);
		controller.setProjectDataService(projectDataService);
		controller.setAdapter(new FriendAdapter());
	}
	
	@Test
	public void getFriends_should_return_empty_response_with_no_friend() {
		// given
		final String userId = "e13bc457676e57efa42";
		final DBUserInfos dbUser = new DBUserInfos();
		
		when(userDataService.findInfosById(userId)).thenReturn(dbUser);
		
		// when
		final FriendsListResponse response = controller.getFriends(userId);
		
		// then
		assertThat(response).isNotNull();
		assertThat(response.getFriends()).isEmpty();
	}
	
	@Test
	public void getFriends_should_return_all_friends() {
		// given
		final String userId = "e13bc457676e57efa42";
		final String friendId1 = "ba34665f6e78bc21351";
		final String friendId2 = "ba34665f6e78bc21352";
		final String friendId3 = "ba34665f6e78bc21353";
		
		final DBUserInfos dbUser = new DBUserInfos();
		dbUser.getFriends().add(friendId1);
		dbUser.getFriends().add(friendId2);
		dbUser.getFriends().add(friendId3);
		
		final List<DBFriend> dbFriends = newArrayList(
				createFriend(friendId1, "avatar1", "username1", true, new Date()),
				createFriend(friendId2, "avatar2", "username2", false, new Date()),
				createFriend(friendId3, "avatar3", "username3", true, new Date()));

		when(userDataService.findInfosById(userId)).thenReturn(dbUser);
		when(userDataService.findFriends(dbUser.getFriends())).thenReturn(dbFriends);
		when(projectDataService.countUserProjects(friendId1)).thenReturn(0L);
		when(projectDataService.countUserProjects(friendId2)).thenReturn(2L);
		when(projectDataService.countUserProjects(friendId3)).thenReturn(4L);
		
		// when
		final FriendsListResponse response = controller.getFriends(userId);
		
		// then
		assertThat(response.getFriends()).hasSize(3);
		
		for(int i = 0; i < response.getFriends().size(); ++i) {			
			FriendResponse actualFriend = response.getFriends().get(i);
			DBFriend expectedFriend = dbFriends.get(i);
			assertThat(actualFriend.getId()).isEqualTo(expectedFriend.getId());
			assertThat(actualFriend.getUsername()).isEqualTo(expectedFriend.getUsername());
			assertThat(actualFriend.getAvatar()).isEqualTo(expectedFriend.getAvatar());
			assertThat(actualFriend.isProfessional()).isEqualTo(expectedFriend.isProfessionnal());
			assertThat(actualFriend.getCreationDate()).isEqualTo(expectedFriend.getCreationDate());
			assertThat(actualFriend.getNbAchievements()).isEqualTo(0L);
		}
		
		assertThat(response.getFriends().get(0).getNbProjects()).isEqualTo(0L);
		assertThat(response.getFriends().get(1).getNbProjects()).isEqualTo(2L);
		assertThat(response.getFriends().get(2).getNbProjects()).isEqualTo(4L);
	}

	private DBFriend createFriend(final String friendId, final String avatar, final String username,
			final boolean isProfessional, final Date creationDate) {
		final DBFriend dbFriend = new DBFriend();
		dbFriend.setId(friendId);
		dbFriend.setAvatar(avatar);
		dbFriend.setUsername(username);
		dbFriend.setProfessionnal(isProfessional);
		dbFriend.setCreationDate(creationDate);
		return dbFriend;
	}

}
