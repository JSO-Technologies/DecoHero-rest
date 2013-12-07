package com.jso.deco.service.user;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.UserResgisterRequest;


public class UserServiceValidatorTest {
	private final UserServiceValidator validator = new UserServiceValidator();
	
	@Test
	public void validateCreationValues_should_pass() throws Exception {
		//given
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstName("azerty");
		request.setLastName("azerty");
		request.setPassword("azerty");
		request.setBirthDate(new Date());
		
		//when
		validator.validateCreationValues(request);
		
		//then
	}
	
	@Test
	public void validateCreationValues_should_fail_coz_username() {
		//given
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername(null);
		request.setEmail("azerty@azerty.com");
		request.setFirstName("azerty");
		request.setLastName("azerty");
		request.setPassword("azerty");
		request.setBirthDate(new Date());
		
		//when
		try {
			validator.validateCreationValues(request);
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
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("");
		request.setFirstName("azerty");
		request.setLastName("azerty");
		request.setPassword("azerty");
		request.setBirthDate(new Date());
		
		//when
		try {
			validator.validateCreationValues(request);
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
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstName(" ");
		request.setLastName("azerty");
		request.setPassword("azerty");
		request.setBirthDate(new Date());
		
		//when
		try {
			validator.validateCreationValues(request);
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
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstName("azerty");
		request.setLastName(null);
		request.setPassword("azerty");
		request.setBirthDate(new Date());
		
		//when
		try {
			validator.validateCreationValues(request);
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
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstName("azerty");
		request.setLastName("azerty");
		request.setPassword(" ");
		request.setBirthDate(new Date());
		
		//when
		try {
			validator.validateCreationValues(request);
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
		UserResgisterRequest request = new UserResgisterRequest();
		request.setUsername("azerty");
		request.setEmail("azerty@azerty.com");
		request.setFirstName("azerty");
		request.setLastName("azerty");
		request.setPassword("azerty");
		request.setBirthDate(null);
		
		//when
		try {
			validator.validateCreationValues(request);
			fail();
		} 
		//then
		catch (DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(DHMessageCode.MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("birthdate");
		}
	}
}
