package com.jso.deco.controller;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.jso.deco.api.common.Category.RM;
import static com.jso.deco.api.common.Room.ENT;
import static com.jso.deco.api.exception.DHMessageCode.PROJECT_DOESNT_EXIST;
import static com.jso.deco.controller.ProjectController.NB_LATEST_PROJECTS;
import static com.jso.deco.controller.ProjectController.NB_LATEST_PROJECT_IDEAS;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jso.deco.api.controller.Author;
import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.controller.LatestProjectIdeasResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.controller.adapter.ProjectAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBProjectIdea;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.ProjectIdeaDataService;
import com.jso.deco.data.service.UserDataService;


public class ProjectControllerTest {
	private final ProjectController controller = new ProjectController();
	private final UserDataService userDataService = mock(UserDataService.class);
	private final ProjectDataService projectDataService = mock(ProjectDataService.class);
	private final ProjectIdeaDataService projectIdeaDataService = mock(ProjectIdeaDataService.class);
	private final ImageService imageService = mock(ImageService.class);
	private final ProjectAdapter projectAdapter = mock(ProjectAdapter.class);
	
	@Before
	public void init() {
		controller.setUserDataService(userDataService);
		controller.setProjectDataService(projectDataService);
		controller.setProjectIdeaDataService(projectIdeaDataService);
		controller.setAdapter(projectAdapter);
		controller.setImageService(imageService);
	}
	
	@Test
	public void createProject_should_save_images_create_project_and_update_user() throws DHServiceException {
		//given
		final String userId = "userId";
		final List<String> imgDataUrls = Lists.newArrayList("image1", "image2");
		final Map<String, String> imgWithIds = Maps.newHashMapWithExpectedSize(2);
		imgWithIds.put("0000000000", "image1");
		imgWithIds.put("1111111111", "image2");
		
		final ProjectCreationRequest request = new ProjectCreationRequest();
		request.setImages(imgDataUrls);
		request.setTitle("Project title");
		request.setDescription("Project description");
		request.setCategory(RM);
		request.setRoom(ENT);
		
		final DBProject dbProject = new DBProject();
		dbProject.setId("projectId");

		when(imageService.generateIds(imgDataUrls)).thenReturn(imgWithIds);
		when(projectAdapter.projectCreationRequestToDBProject(request, imgWithIds.keySet(), userId)).thenReturn(dbProject);
		doNothing().when(projectDataService).create(Mockito.any(DBProject.class));
		doNothing().when(imageService).saveProjectImg(dbProject.getId(), imgWithIds);
		
		//when
		final CreateProjectResponse response = controller.createProject(userId, request);
		
		//then
		assertThat(response.getId()).isEqualTo(dbProject.getId());
		verify(imageService, times(1)).saveProjectImg(dbProject.getId(), imgWithIds);
		verify(projectDataService, times(1)).create(dbProject);
		
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
		final String fromDateString = "3102948586760";
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
	
	@Test
	public void getLatestProjectIdeas_should_return_latest_project_ideas() throws DHServiceException {
		// given
		final String projectId = "45662ca2346f5e6e";
		final String fromDateString = "3102948586760";
		final Date date = new Date();
		final LatestProjectIdeasResponse expectedResponse = new LatestProjectIdeasResponse();
		
		final DBUserInfos user1 = new DBUserInfos();
		user1.setId("1");
		final DBUserInfos user2 = new DBUserInfos();
		user2.setId("2");
		
		final Map<String, DBUserInfos> usersById = newHashMap();
		usersById.put("1", user1);
		usersById.put("2", user2);
		
		final DBProjectIdea projectIdea1 = new DBProjectIdea();
		projectIdea1.setUserId("1");
		final DBProjectIdea projectIdea2 = new DBProjectIdea();
		projectIdea2.setUserId("2");
		
		final List<DBProjectIdea> projectIdeas = newArrayList(projectIdea1, projectIdea2);
		
		when(projectAdapter.fromDateStringToDate(fromDateString)).thenReturn(date);
		when(projectIdeaDataService.findProjectLatest(projectId, NB_LATEST_PROJECT_IDEAS, date)).thenReturn(projectIdeas);
		when(projectAdapter.dbProjectIdeasToLatestProjectIdeasResponse(projectIdeas, usersById)).thenReturn(expectedResponse);
		when(userDataService.findInfosByIds(newHashSet("1", "2"))).thenReturn(newArrayList(user1, user2));
		
		// when
		final LatestProjectIdeasResponse response = controller.getLatestProjectIdeas(projectId, fromDateString);
		
		// then
		assertThat(response).isSameAs(expectedResponse);
	}
}
