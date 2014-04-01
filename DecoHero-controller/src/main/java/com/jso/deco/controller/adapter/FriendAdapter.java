package com.jso.deco.controller.adapter;

import com.jso.deco.api.controller.FriendResponse;
import com.jso.deco.data.api.DBFriend;


public class FriendAdapter {

	public FriendResponse dbFriendToFriendResponse(final DBFriend friend, final long nbProjects, final long nbAchievements) {
		final FriendResponse response = new FriendResponse();
		
		response.setId(friend.getId());
		response.setAvatar(friend.getAvatar());
		response.setUsername(friend.getUsername());
		response.setProfessional(friend.isProfessionnal());
		response.setCreationDate(friend.getCreationDate());
		
		response.setNbProjects(nbProjects);
		response.setNbAchievements(nbAchievements);
		return response;
	}
	
}
