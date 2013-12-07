package com.jso.deco.service.adapter;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jso.deco.api.exception.DHMessageCode;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.response.ErrorMessageResponse;
import com.jso.deco.api.service.response.ServiceResponse;

public class ServiceResponseAdapterTest {
	private final ServiceResponseAdapter adapter = new ServiceResponseAdapter();
	
	@Test
	public void fromException_with_REGISTRATION_USERNAME_ALREADY_EXISTS() throws Exception {
		//given
		String details = "azerty";
		DHServiceException dhServiceException = new DHServiceException(DHMessageCode.REGISTRATION_USERNAME_ALREADY_EXISTS, details);
		
		//when
		ServiceResponse response = adapter.fromException(dhServiceException);
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
		assertThat(response.getContent()).isInstanceOf(ErrorMessageResponse.class);
		ErrorMessageResponse errorMessageResponse = (ErrorMessageResponse) response.getContent();
		assertThat(errorMessageResponse.getError()).isEqualTo(DHMessageCode.REGISTRATION_USERNAME_ALREADY_EXISTS.name());
		assertThat(errorMessageResponse.getDetails()).isEqualTo(details);
	}
	
	@Test
	public void fromException_with_REGISTRATION_EMAIL_ALREADY_EXISTS() throws Exception {
		//given
		String details = "azerty";
		DHServiceException dhServiceException = new DHServiceException(DHMessageCode.REGISTRATION_EMAIL_ALREADY_EXISTS, details);
		
		//when
		ServiceResponse response = adapter.fromException(dhServiceException);
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
		assertThat(response.getContent()).isInstanceOf(ErrorMessageResponse.class);
		ErrorMessageResponse errorMessageResponse = (ErrorMessageResponse) response.getContent();
		assertThat(errorMessageResponse.getError()).isEqualTo(DHMessageCode.REGISTRATION_EMAIL_ALREADY_EXISTS.name());
		assertThat(errorMessageResponse.getDetails()).isEqualTo(details);
	}
	
	@Test
	public void fromException_with_MISSING_FIELD() throws Exception {
		//given
		String details = "azerty";
		DHServiceException dhServiceException = new DHServiceException(DHMessageCode.MISSING_FIELD, details);
		
		//when
		ServiceResponse response = adapter.fromException(dhServiceException);
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(response.getContent()).isInstanceOf(ErrorMessageResponse.class);
		ErrorMessageResponse errorMessageResponse = (ErrorMessageResponse) response.getContent();
		assertThat(errorMessageResponse.getError()).isEqualTo(DHMessageCode.MISSING_FIELD.name());
		assertThat(errorMessageResponse.getDetails()).isEqualTo(details);
	}
	
}
