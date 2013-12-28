package com.jso.deco.service.adapter;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.jso.deco.api.service.request.UserLoginRequest;
import com.jso.deco.api.service.request.UserRegisterRequest;
import com.jso.deco.service.utils.Encoder;


public class UserServiceAdapterTest {
	private final UserServiceAdapter adapter = new UserServiceAdapter();
	private final Encoder encoder = new Encoder();
	
	@Before
	public void init() {
		adapter.setEncoder(encoder);
	}
	
	@Test
	public void adapt_UserRegisterRequest() {
		//given
		Date now = new Date();
		
		UserRegisterRequest request = new UserRegisterRequest();
		request.setEmail("Jimmy.somsANith@Gmail.com");
		request.setUsername("JSomsanith");
		request.setFirstname("Jimmy");
		request.setLastname("Somsanith");
		request.setBirthdateTimestamp(now.getTime());
		request.setPassword("azerty123");
		
		//when
		adapter.adapt(request);
		
		//then
		assertThat(request.getEmail()).isEqualTo("jimmy.somsanith@gmail.com");
		assertThat(request.getUsername()).isEqualTo("JSomsanith");
		assertThat(request.getFirstname()).isEqualTo("Jimmy");
		assertThat(request.getLastname()).isEqualTo("Somsanith");
		assertThat(request.getBirthdate()).isEqualTo(now);
		assertThat(request.getPassword()).isEqualTo(new String(encoder.digestSHA1("azerty123".getBytes())));
	}
	
	@Test
	public void adapt_UserLoginRequest() {
		//given
		UserLoginRequest request = new UserLoginRequest();
		request.setEmail("Jimmy.somsANith@Gmail.com");
		request.setPassword("azerty123");
		
		//when
		adapter.adapt(request);
		
		//then
		assertThat(request.getEmail()).isEqualTo("jimmy.somsanith@gmail.com");
		assertThat(request.getPassword()).isEqualTo(new String(encoder.digestSHA1("azerty123".getBytes())));
	}
}
