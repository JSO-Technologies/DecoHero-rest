package com.jso.deco.controller;

import java.util.List;

import com.jso.deco.api.controller.CreateProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.controller.adapter.ProjectAdapter;
import com.jso.deco.controller.image.ImageService;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.service.ProjectDataService;
import com.jso.deco.data.service.UserDataService;


public class ProjectController {
	private ProjectAdapter adapter;
	private UserDataService userDataService;
	private ProjectDataService projectDataService;
	private ImageService imageService;
	
	public CreateProjectResponse createProject(String userId, ProjectCreationRequest request) throws DHServiceException {
		
		List<String> imgIds = imageService.saveProjectImg(request.getImages());
		
		DBProject dbProject = adapter.projectCreationRequestToDBProject(request, imgIds);
		projectDataService.create(dbProject);
		String projectId = dbProject.getId();
		
		imageService.moveProjectImg(projectId, imgIds);
		
		userDataService.addProjects(userId, projectId);
		
		//create response
		CreateProjectResponse response = new CreateProjectResponse();
		response.setId(projectId);
		return response;
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
