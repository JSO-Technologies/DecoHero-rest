package com.jso.deco.controller.adapter;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static com.jso.deco.api.exception.DHMessageCode.MISSING_FIELD;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBUserInfos;

public class ProjectAdapterTest {
	private final ProjectAdapter adapter = new ProjectAdapter();
	
	@Test
	public void projectCreationRequestToDBProject_should_adapt_all_fields() {
		//given
		final ProjectCreationRequest request = new ProjectCreationRequest();
		request.setCategory(RM);
		request.setRoom(ATC);
		request.setTitle("Title");
		request.setDescription("Description");
		
		final List<String> imgIds = Lists.newArrayList("0000000000", "1111111111");
		final String userId = "1zaihe134jlaih";
		
		//when
		final DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds, userId);
		
		//then
		assertThat(dbProject.getTitle()).isEqualTo(request.getTitle());
		assertThat(dbProject.getDescription()).isEqualTo(request.getDescription());
		assertThat(dbProject.getCategory()).isEqualTo(request.getCategory().name());
		assertThat(dbProject.getRoom()).isEqualTo(request.getRoom().name());
		assertThat(dbProject.getImages()).isEqualTo(imgIds);
		assertThat(dbProject.getUserId()).isEqualTo(userId);
	}
	
	@Test
	public void dbProjectToProjectResponse_should_adapt_all_fields() {
		//given
		final DBProject project = new DBProject();
		project.setId("azekrhaueh");
		project.setCategory("RM");
		project.setCategory("ATC");
		project.setTitle("title");
		project.setDescription("description");
		project.getImages().add("image1");
		project.getImages().add("image2");
		
		final DBUserInfos authorUser = new DBUserInfos();
		authorUser.setId("qsdmlkqsdmlk");
		authorUser.setUsername("username");
		authorUser.setAvatar("aaaaapppppppmmmmmmm");
		authorUser.setProfessionnal(true);
		
		//when
		final ProjectResponse result = adapter.dbProjectToProjectResponse(project, authorUser);
		
		//then
		assertThat(result.getId()).isEqualTo(project.getId());
		assertThat(result.getCategory()).isEqualTo(project.getCategory());
		assertThat(result.getRoom()).isEqualTo(project.getRoom());
		assertThat(result.getTitle()).isEqualTo(project.getTitle());
		assertThat(result.getDescription()).isEqualTo(project.getDescription());
		assertThat(result.getImages()).containsOnly(project.getImages().toArray());
		assertThat(result.getAuthor().getId()).isEqualTo(authorUser.getId());
		assertThat(result.getAuthor().getUsername()).isEqualTo(authorUser.getUsername());
		assertThat(result.getAuthor().getAvatar()).isEqualTo(authorUser.getAvatar());
		assertThat(result.getAuthor().isProfessional()).isEqualTo(authorUser.isProfessionnal());
		
	}
	
	@Test
	public void fromDateStringToDate_should_convert_date() throws DHServiceException {
		// given
		final String fromDateString = "20140101211258";
		
		// when
		final Date resultingDate = adapter.fromDateStringToDate(fromDateString);
		
		// then
		DateTime dateTime = new DateTime(resultingDate); 
		assertThat(dateTime.getYear()).isEqualTo(2014);
		assertThat(dateTime.getMonthOfYear()).isEqualTo(1);
		assertThat(dateTime.getDayOfMonth()).isEqualTo(1);
		assertThat(dateTime.getHourOfDay()).isEqualTo(21);
		assertThat(dateTime.getMinuteOfHour()).isEqualTo(12);
		assertThat(dateTime.getSecondOfMinute()).isEqualTo(58);
	}
	
	@Test
	public void fromDateStringToDate_should_throw_exception_with_invalid_format() {
		// given
		final String fromDateString = "201401a";
		
		// when
		try {
			adapter.fromDateStringToDate(fromDateString);
			fail("Should have thrown DHServiceException with invalid date");
		}
		// then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(MISSING_FIELD);
			assertThat(e.getDetails()).isEqualTo("fromDate");
		}
	}
}
