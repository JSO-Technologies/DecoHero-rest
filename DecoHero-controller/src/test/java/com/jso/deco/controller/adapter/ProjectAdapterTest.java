package com.jso.deco.controller.adapter;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.jso.deco.api.controller.LatestProjectIdeasResponse;
import com.jso.deco.api.controller.ProjectIdeaResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBProjectIdea;
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
	
	@Test
	public void dbProjectIdeaToCreateProjectIdeaResponse_should_adapt_all_fields() {
		// given
		final DBUserInfos user = createDBUserInfos("1", "jsomsanith", "9234a2c34cfa-abb102393-123", false);
		final DBProjectIdea idea = createIdea("42", "Description here", newArrayList("4efe420342-f9cb34-2345", "6eefe420342-f9cb34-238e"), new Date(), "1");
		
		// when
		final ProjectIdeaResponse response = adapter.dbProjectIdeaToCreateProjectIdeaResponse(user, idea);
		
		// then
		assertThat(response.getId()).isEqualTo(idea.getId());
		assertThat(response.getDescription()).isEqualTo(idea.getDescription());
		assertThat(response.getImages()).containsOnly(idea.getImages().toArray());
		assertThat(response.getCreationDate()).isEqualTo(idea.getCreationDate());
		assertThat(response.getAuthor().getId()).isEqualTo(user.getId());
		assertThat(response.getAuthor().getUsername()).isEqualTo(user.getUsername());
		assertThat(response.getAuthor().getAvatar()).isEqualTo(user.getAvatar());
		assertThat(response.getAuthor().isProfessional()).isEqualTo(user.isProfessionnal());
				
	}
	
	@Test
	public void dbProjectIdeasToLatestProjectIdeasResponse_should_adapt_each_element() {
		// given
		final DBUserInfos user1 = createDBUserInfos("1", "jsomsanith1", "9234a2c34cfa-abb102393-123", false);
		final DBUserInfos user2 = createDBUserInfos("2", "jsomsanith2", "9234a2c34cfa-abb102393-124", false);
		final DBUserInfos user3 = createDBUserInfos("3", "jsomsanith3", "9234a2c34cfa-abb102393-125", true);
		
		final DBProjectIdea idea1 = createIdea("42", "Description here", newArrayList("4efe420342-f9cb34-2345", "6eefe420342-f9cb34-238e"), new Date(), "1");
		final DBProjectIdea idea2 = createIdea("111", "Description here", newArrayList("4efe420342-f9cb34-4524", "6eefe420342-f9cb34-aaae"), new Date(), "3");
		final DBProjectIdea idea3 = createIdea("30", "Description here", new ArrayList<String>(), new Date(), "2");
		
		final List<DBProjectIdea> ideas = newArrayList(idea1, idea2, idea3);
		final Map<String, DBUserInfos> users = newHashMap();
		users.put("1", user1);
		users.put("2", user2);
		users.put("3", user3);
		
		// when
		final LatestProjectIdeasResponse response = adapter.dbProjectIdeasToLatestProjectIdeasResponse(ideas, users);
		
		// then
		assertThat(response.getIdeas()).hasSize(3);

		for(int i = 0; i < response.getIdeas().size(); ++i) {
			final DBProjectIdea dbProjectIdea = ideas.get(i);
			final ProjectIdeaResponse actualIdea = response.getIdeas().get(i);
			assertThat(actualIdea.getId()).isEqualTo(dbProjectIdea.getId());
			assertThat(actualIdea.getDescription()).isEqualTo(dbProjectIdea.getDescription());
			assertThat(actualIdea.getImages()).containsOnly(dbProjectIdea.getImages().toArray());
			assertThat(actualIdea.getCreationDate()).isEqualTo(dbProjectIdea.getCreationDate());

			final DBUserInfos user = users.get(dbProjectIdea.getUserId());
			assertThat(actualIdea.getAuthor().getId()).isEqualTo(user.getId());
			assertThat(actualIdea.getAuthor().getUsername()).isEqualTo(user.getUsername());
			assertThat(actualIdea.getAuthor().getAvatar()).isEqualTo(user.getAvatar());
			assertThat(actualIdea.getAuthor().isProfessional()).isEqualTo(user.isProfessionnal());		
		}
	}

	private DBProjectIdea createIdea(String id, String description, List<String> images, Date creationDate, String userId) {
		final DBProjectIdea idea = new DBProjectIdea();
		idea.setId(id);
		idea.setUserId(userId);
		idea.setDescription(description);
		idea.getImages().addAll(images);
		idea.setCreationDate(creationDate);
		return idea;
	}

	private DBUserInfos createDBUserInfos(String id, String username, String avatar, boolean professional) {
		final DBUserInfos user = new DBUserInfos();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		user.setProfessionnal(professional);
		return user;
	}
}
