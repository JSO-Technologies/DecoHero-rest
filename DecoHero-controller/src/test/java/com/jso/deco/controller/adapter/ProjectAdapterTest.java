package com.jso.deco.controller.adapter;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Maps;
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
		
		final Map<String, String> imgWithIds = Maps.newHashMapWithExpectedSize(2);
		imgWithIds.put("0000000000", "image1");
		imgWithIds.put("1111111111", "image2");
		final String userId = "1zaihe134jlaih";
		
		//when
		final DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgWithIds.keySet(), userId);
		
		//then
		assertThat(dbProject.getTitle()).isEqualTo(request.getTitle());
		assertThat(dbProject.getDescription()).isEqualTo(request.getDescription());
		assertThat(dbProject.getCategory()).isEqualTo(request.getCategory().name());
		assertThat(dbProject.getRoom()).isEqualTo(request.getRoom().name());
		assertThat(dbProject.getImages()).containsOnly(imgWithIds.keySet().toArray());
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
		final String fromDateString = "1391970342000";
		
		// when
		final Date resultingDate = adapter.fromDateStringToDate(fromDateString);
		
		// then
		DateTime dateTime = new DateTime(resultingDate); 
		assertThat(dateTime.getYear()).isEqualTo(2014);
		assertThat(dateTime.getMonthOfYear()).isEqualTo(2);
		assertThat(dateTime.getDayOfMonth()).isEqualTo(9);
		assertThat(dateTime.getHourOfDay()).isEqualTo(19);
		assertThat(dateTime.getMinuteOfHour()).isEqualTo(25);
		assertThat(dateTime.getSecondOfMinute()).isEqualTo(42);
	}
}
