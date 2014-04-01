package com.jso.deco.controller;

import java.util.List;

import com.jso.deco.api.controller.FriendResponse;
import com.jso.deco.api.controller.FriendsListResponse;
import com.jso.deco.controller.adapter.FriendAdapter;
import com.jso.deco.data.api.DBFriend;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.UserDataService;

public class FriendsController {
	private FriendAdapter adapter;
	private UserDataService userDataService;
	private ProjectDataService projectDataService;
	
	public FriendsListResponse getFriends(final String userId) {
		final DBUserInfos user = userDataService.findInfosById(userId);
		final List<String> friendsIds = user.getFriends();
		
		final FriendsListResponse response = new FriendsListResponse();
		if(! friendsIds.isEmpty()) {
			final List<DBFriend> friends = userDataService.findFriends(friendsIds);
			
			for(DBFriend friend : friends) {
				final long nbProjects = projectDataService.countUserProjects(friend.getId());
				final FriendResponse adaptedFriend = adapter.dbFriendToFriendResponse(friend, nbProjects, 0);
				response.getFriends().add(adaptedFriend);
			}
		}
		return response;
	}

	public FriendAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(FriendAdapter adapter) {
		this.adapter = adapter;
	}
	public UserDataService getUserDataService() {
		return userDataService;
	}
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}
	public ProjectDataService getProjectDataService() {
		return projectDataService;
	}
	public void setProjectDataService(ProjectDataService projectDataService) {
		this.projectDataService = projectDataService;
	}
}
