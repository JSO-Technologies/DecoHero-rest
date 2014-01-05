package com.jso.deco.api.controller;

public class UpdateAvatarResponse {
	private final String avatar;
	
	public UpdateAvatarResponse(String avatar) {
		this.avatar = avatar;
	}
	
	public String getAvatar() {
		return avatar;
	}
}
