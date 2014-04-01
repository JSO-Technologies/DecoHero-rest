package com.jso.deco.api.controller;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class FriendsListResponse {
	private final List<FriendResponse> friends = newArrayList();

	public List<FriendResponse> getFriends() {
		return friends;
	}
	
	public void addFriend(final FriendResponse friend) {
		friends.add(friend);
	}
}
