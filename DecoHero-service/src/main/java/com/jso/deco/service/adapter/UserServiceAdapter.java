package com.jso.deco.service.adapter;

import java.util.Date;

import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.service.utils.Encoder;

public class UserServiceAdapter {
	private Encoder encoder;
	
	public void adapt(UserRegisterRequest request) {
		request.setBirthdate(new Date(request.getBirthdateTimestamp()));
		request.setPassword(new String(encoder.digestSHA1(request.getPassword().getBytes())));
	}
	
	public void adapt(UserLoginRequest request) {
		request.setPassword(new String(encoder.digestSHA1(request.getPassword().getBytes())));
	}

	public Encoder getEncoder() {
		return encoder;
	}
	
	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}
}
