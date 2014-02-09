package com.jso.deco.controller.adapter;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBUserInfos;

public class ProjectAdapterTest {
	private final ProjectAdapter adapter = new ProjectAdapter();
	
	@Test
	public void projectCreationRequestToDBProject_should_adapt_all_fields() {
		//given
		ProjectCreationRequest request = new ProjectCreationRequest();
		request.setCategory(RM);
		request.setRoom(ATC);
		request.setTitle("Title");
		request.setDescription("Description");
		
		List<String> imgIds = Lists.newArrayList("0000000000", "1111111111");
		String userId = "1zaihe134jlaih";
		
		//when
		DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds, userId);
		
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
		DBProject project = new DBProject();
		project.setId("azekrhaueh");
		project.setCategory("RM");
		project.setCategory("ATC");
		project.setTitle("title");
		project.setDescription("description");
		project.getImages().add("image1");
		project.getImages().add("image2");
		
		DBUserInfos authorUser = new DBUserInfos();
		authorUser.setId("qsdmlkqsdmlk");
		authorUser.setUsername("username");
		authorUser.setAvatar("aaaaapppppppmmmmmmm");
		authorUser.setProfessionnal(true);
		
		//when
		ProjectResponse result = adapter.dbProjectToProjectResponse(project, authorUser);
		
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
}
