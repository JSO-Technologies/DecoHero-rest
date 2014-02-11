package com.jso.deco.controller;

import static com.jso.deco.api.exception.DHMessageCode.PROJECT_DOESNT_EXIST;

import java.util.List;

import com.jso.deco.api.controller.CreateProjectResponse;
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
	public CreateProjectResponse createProject(String userId, ProjectCreationRequest request) throws DHServiceException {
		
		List<String> imgIds = imageService.saveProjectImg(request.getImages());
		DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds, userId);

		projectDataService.create(dbProject);
		String projectId = dbProject.getId();
		imageService.moveProjectImg(projectId, imgIds);
		userDataService.addProjects(userId, projectId);
		
		CreateProjectResponse response = new CreateProjectResponse();
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
	public ProjectResponse getProject(String projectId) throws DHServiceException {
		DBProject project = projectDataService.find(projectId);
		if(project == null) {
			throw new DHServiceException(PROJECT_DOESNT_EXIST, null);
		}
		
		DBUserInfos author = userDataService.findInfosById(project.getUserId());
		
		return adapter.dbProjectToProjectResponse(project, author);
	}

	/**
	 * Get project image
	 * @param projectId
	 * @param imageId
	 * @return
	 */
	public byte[] getImage(String projectId, String imageId) {
		return imageService.getProjectImage(projectId, imageId);
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
