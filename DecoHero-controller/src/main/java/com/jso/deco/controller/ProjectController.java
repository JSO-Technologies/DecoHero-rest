package com.jso.deco.controller;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.jso.deco.api.exception.DHMessageCode.PROJECT_DOESNT_EXIST;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.controller.LatestProjectIdeasResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.ProjectIdeaResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.api.service.request.ProjectIdeaCreationRequest;
import com.jso.deco.controller.adapter.ProjectAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBProjectIdea;
import com.jso.deco.data.api.DBUserInfos;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.ProjectIdeaDataService;
import com.jso.deco.data.service.UserDataService;


public class ProjectController {
	protected static final int NB_LATEST_PROJECTS = 5;
	protected static final int NB_LATEST_PROJECT_IDEAS = 5;
	
	private ProjectAdapter adapter;
	private UserDataService userDataService;
	private ProjectDataService projectDataService;
	private ProjectIdeaDataService projectIdeaDataService;
	private ImageService imageService;
	
	/**
	 * Create a new project
	 * @param userId
	 * @param request
	 * @return
	 * @throws DHServiceException
	 */
	public CreateProjectResponse createProject(final String userId, final ProjectCreationRequest request) throws DHServiceException {
		final Map<String, String> imgs = imageService.generateIds(request.getImages());
		final DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgs.keySet(), userId);

		projectDataService.create(dbProject);
		
		final String projectId = dbProject.getId();
		imageService.saveProjectImg(projectId, imgs);
		userDataService.addProjects(userId, projectId);
		
		final CreateProjectResponse response = new CreateProjectResponse();
		response.setId(projectId);
		return response;
	}
	
	/**
	 * Get an existing project infos
	 * @param userId
	 * @param projectId
	 * @return
	 * @throws DHServiceException 
	 */
	public ProjectResponse getProject(final String projectId) throws DHServiceException {
		final DBProject project = projectDataService.find(projectId);
		if(project == null) {
			throw new DHServiceException(PROJECT_DOESNT_EXIST, null);
		}
		
		final DBUserInfos author = userDataService.findInfosById(project.getUserId());
		
		return adapter.dbProjectToProjectResponse(project, author);
	}

	/**
	 * Get project image
	 * @param projectId
	 * @param imageId
	 * @return
	 */
	public byte[] getImage(final String projectId, final String imageId) {
		return imageService.getProjectImage(projectId, imageId);
	}
	
	/**
	 * Get the lastest projects of a specific user
	 * @param userId
	 * @param fromDate
	 * @return
	 * @throws DHServiceException 
	 */
	public LatestProjectResponse getLastestProjects(final String userId, final String fromDateString) throws DHServiceException {
		final Date fromDate = adapter.fromDateStringToDate(fromDateString);
		
		final List<DBProject> dbProjects = projectDataService.findUserLatest(userId, NB_LATEST_PROJECTS, fromDate);
		
		return adapter.dbProjectsToLatestProjectResponse(dbProjects);
	}
	
	/**
	 * Create a project idea
	 * @param projectId
	 * @param request
	 * @param userId 
	 * @return
	 * @throws DHServiceException 
	 */
	public ProjectIdeaResponse createProjectIdea(final String userId, final String projectId, final ProjectIdeaCreationRequest request) throws DHServiceException {
		if(! projectDataService.exists(projectId)) {
			throw new DHServiceException(PROJECT_DOESNT_EXIST, "Project does not exist");
		}
		
		final Map<String, String> imgs = imageService.generateIds(request.getImages());
		
		final DBProjectIdea idea = adapter.projectIdeaCreationRequestToDBProjectIdea(userId, projectId, request, imgs.keySet());
		projectIdeaDataService.create(idea);
		
		imageService.saveProjectIdeaImg(projectId, idea.getId(), imgs);
		
		final DBUserInfos user = userDataService.findInfosById(userId);
		
		return adapter.dbProjectIdeaToCreateProjectIdeaResponse(user, idea);
	}

	/**
	 * Get the lastest project ideas of a specific project
	 * @param projectId
	 * @param fromDate
	 * @return
	 * @throws DHServiceException 
	 */
	public LatestProjectIdeasResponse getLatestProjectIdeas(String projectId, String fromDateString) throws DHServiceException {
		final Date fromDate = adapter.fromDateStringToDate(fromDateString);
		
		final List<DBProjectIdea> dbProjectIdeas = projectIdeaDataService.findProjectLatest(projectId, NB_LATEST_PROJECT_IDEAS, fromDate);
		
		final Set<String> userIds = newHashSet(); 
		for(DBProjectIdea idea : dbProjectIdeas) {
			userIds.add(idea.getUserId());
		}
		final List<DBUserInfos> dbUsers = userDataService.findInfosByIds(userIds);
		
		final Map<String, DBUserInfos> dbUsersById = newHashMap();
		for(DBUserInfos dbUser: dbUsers) {
			dbUsersById.put(dbUser.getId(), dbUser);
		}
		
		return adapter.dbProjectIdeasToLatestProjectIdeasResponse(dbProjectIdeas, dbUsersById);
	}
	
	public void setAdapter(ProjectAdapter adapter) {
		this.adapter = adapter;
	}
	
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}
	
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	public void setProjectDataService(ProjectDataService projectDataService) {
		this.projectDataService = projectDataService;
	}
	
	public void setProjectIdeaDataService(ProjectIdeaDataService projectIdeaDataService) {
		this.projectIdeaDataService = projectIdeaDataService;
	}
}
