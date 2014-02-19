package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.PROJECT_DOESNT_EXIST;

import java.util.Date;
import java.util.List;

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


public class ProjectController {
	protected static final int NB_LATEST_PROJECTS = 5;
	private ProjectAdapter adapter;
	private UserDataService userDataService;
	private ProjectDataService projectDataService;
	private ImageService imageService;
	
	/**
	 * Create a new project
	 * @param userId
	 * @param request
	 * @return
	 * @throws DHServiceException
	 */
	public CreateProjectResponse createProject(final String userId, final ProjectCreationRequest request) throws DHServiceException {
		
		final List<String> imgIds = imageService.saveProjectImg(request.getImages());
		final DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds, userId);

		projectDataService.create(dbProject);
		final String projectId = dbProject.getId();
		imageService.moveProjectImg(projectId, imgIds);
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
}
