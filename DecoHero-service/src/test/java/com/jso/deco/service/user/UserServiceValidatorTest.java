package com.jso.deco.service.user;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserRegisterRequest;


public class UserServiceValidatorTest {
	private final UserServiceValidator validator = new UserServiceValidator();
	
	@Test
	public void validateCreationValues_should_pass() throws Exception {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstname("azerty");
		request.setLastname("azerty");
		request.setPassword("azerty");
		request.setBirthdateTimestamp(1L);
		
		//when
		validator.validate(request);
		
		//then
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_username() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername(null);
		request.setEmail("azerty@azerty.com");
		request.setFirstname("azerty");
		request.setLastname("azerty");
		request.setPassword("azerty");
		request.setBirthdate(new Date());
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("username");
		}
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_email() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("");
		request.setFirstname("azerty");
		request.setLastname("azerty");
		request.setPassword("azerty");
		request.setBirthdate(new Date());
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("email");
		}
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_firstname() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstname(" ");
		request.setLastname("azerty");
		request.setPassword("azerty");
		request.setBirthdate(new Date());
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("firstname");
		}
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_lastname() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstname("azerty");
		request.setLastname(null);
		request.setPassword("azerty");
		request.setBirthdate(new Date());
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("lastname");
		}
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_password() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstname("azerty");
		request.setLastname("azerty");
		request.setPassword(" ");
		request.setBirthdate(new Date());
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("password");
		}
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_birthdate() {
		//given
		UserRegisterRequest request = new UserRegisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstname("azerty");
		request.setLastname("azerty");
		request.setPassword("azerty");
		request.setBirthdate(null);
		
		//when
		try {
			validator.validate(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("birthdate");
		}
	}
}
