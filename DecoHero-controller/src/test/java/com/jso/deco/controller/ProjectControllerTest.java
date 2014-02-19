package com.jso.deco.controller;

import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ENT;
import static com.jso.deco.api.exception.DHMessageCode.PROJECT_DOESNT_EXIST;
import static com.jso.deco.controller.ProjectController.NB_LATEST_PROJECTS;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;
import com.jso.deco.api.controller.Author;
import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.controller.adapter.ProjectAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.UserDataService;


public class ProjectControllerTest {
	private final ProjectController controller = new ProjectController();
	private final UserDataService userDataService = mock(UserDataService.class);
	private final ProjectDataService projectDataService = mock(ProjectDataService.class);
	private final ImageService imageService = mock(ImageService.class);
	private final ProjectAdapter projectAdapter = mock(ProjectAdapter.class);
	
	@Before
	public void init() {
		controller.setUserDataService(userDataService);
		controller.setProjectDataService(projectDataService);
		controller.setAdapter(projectAdapter);
		controller.setImageService(imageService);
	}
	
	@Test
	public void createProject_should_save_images_create_project_and_update_user() throws DHServiceException {
		//given
		final String userId = "userId";
		final List<String> imgDataUrls = Lists.newArrayList("image1", "image2");
		final List<String> imgIds = Lists.newArrayList("0000000000", "1111111111");
		
		final ProjectCreationRequest request = new ProjectCreationRequest();
		request.setImages(imgDataUrls);
		request.setTitle("Project title");
		request.setDescription("Project description");
		request.setCategory(RM);
		request.setRoom(ENT);
		
		final DBProject dbProject = new DBProject();
		dbProject.setId("projectId");

		when(imageService.saveProjectImg(imgDataUrls)).thenReturn(imgIds);
		when(projectAdapter.projectCreationRequestToDBProject(request, imgIds, userId)).thenReturn(dbProject);
		doNothing().when(projectDataService).create(Mockito.any(DBProject.class));
		doNothing().when(imageService).moveProjectImg(dbProject.getId(), imgIds);
		doNothing().when(userDataService).addProjects(userId, dbProject.getId());
		
		//when
		final CreateProjectResponse response = controller.createProject(userId, request);
		
		//then
		assertThat(response.getId()).isEqualTo(dbProject.getId());
		verify(imageService, times(1)).saveProjectImg(request.getImages());
		verify(projectDataService, times(1)).create(dbProject);
		verify(imageService, times(1)).moveProjectImg(dbProject.getId(), imgIds);
		verify(userDataService, times(1)).addProjects(userId, dbProject.getId());
		
	}
	
	@Test
	public void getProject_should_return_project_infos() throws DHServiceException {
		//given
		final String projectId = "1ab349fe394a958";
		final String userId = "45662ca2346f5e6e";
		
		final DBProject dbProject = new DBProject();
		dbProject.setId(projectId);
		dbProject.setUserId(userId);

		final DBUserInfos dbUserInfos = new DBUserInfos();
		dbUserInfos.setId(userId);

		final ProjectResponse projectResponse = new ProjectResponse();
		projectResponse.setId(projectId);
		projectResponse.setAuthor(new Author());
		projectResponse.getAuthor().setId(userId);

		when(projectDataService.find(projectId)).thenReturn(dbProject);
		when(userDataService.findInfosById(userId)).thenReturn(dbUserInfos);
		when(projectAdapter.dbProjectToProjectResponse(dbProject, dbUserInfos)).thenReturn(projectResponse);
		
		//when
		final ProjectResponse response = controller.getProject(projectId);
		
		//then
		assertThat(response).isSameAs(projectResponse);
	}
	
	@Test
	public void getProject_should_throw_exception_when_project_id_doesnt_exists() {
		//given
		final String projectId = "1ab349fe394a958";
		when(projectDataService.find(projectId)).thenReturn(null);
		
		//when
		try {
			controller.getProject(projectId);
			fail("Should have thrown exception");
		}		
		//then
		catch(DHServiceException e) {
			assertThat(e.getDhMessage()).isEqualTo(PROJECT_DOESNT_EXIST);
		}
	}
	
	@Test
	public void getLastestProjects_should_return_latest_projects() throws DHServiceException {
		// given
		final String userId = "45662ca2346f5e6e";
		final String fromDateString = "20146-01-01";
		final Date date = new Date();
		final LatestProjectResponse expectedResponse = new LatestProjectResponse();
		
		final List<DBProject> projects = Lists.newArrayList(new DBProject(), new DBProject());
		
		when(projectAdapter.fromDateStringToDate(fromDateString)).thenReturn(date);
		when(projectDataService.findUserLatest(userId, NB_LATEST_PROJECTS, date)).thenReturn(projects);
		when(projectAdapter.dbProjectsToLatestProjectResponse(projects)).thenReturn(expectedResponse);
		
		// when
		final LatestProjectResponse response = controller.getLastestProjects(userId, fromDateString);
		
		// then
		assertThat(response).isSameAs(expectedResponse);
	}
}
