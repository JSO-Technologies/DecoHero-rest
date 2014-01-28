package com.jso.deco.controller.adapter;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ATC;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;

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
		
		//when
		DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds);
		
		//then
		assertThat(dbProject.getTitle()).isEqualTo(request.getTitle());
		assertThat(dbProject.getDescription()).isEqualTo(request.getDescription());
		assertThat(dbProject.getCategory()).isEqualTo(request.getCategory().name());
		assertThat(dbProject.getRoom()).isEqualTo(request.getRoom().name());
		assertThat(dbProject.getImages()).isEqualTo(imgIds);
	}
}
