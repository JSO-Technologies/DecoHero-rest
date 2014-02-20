package com.jso.deco.controller.adapter;

import java.util.Date;
import java.util.List;

import com.jso.deco.api.controller.Author;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.LightProject;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBUserInfos;

public class ProjectAdapter {

	public DBProject projectCreationRequestToDBProject(ProjectCreationRequest request, List<String> imgIds, String userId) {
		final DBProject project = new DBProject();
		project.setTitle(request.getTitle());
		project.setDescription(request.getDescription());
		project.setCategory(request.getCategory().name());
		project.setRoom(request.getRoom() != null ? request.getRoom().name() : null);
		project.getImages().addAll(imgIds);
		project.setUserId(userId);
		return project;
	}

	public ProjectResponse dbProjectToProjectResponse(DBProject project, DBUserInfos authorUser) {
		final Author author = new Author();
		author.setId(authorUser.getId());
		author.setUsername(authorUser.getUsername());
		author.setAvatar(authorUser.getAvatar());
		author.setProfessional(authorUser.isProfessionnal());

		final ProjectResponse response = new ProjectResponse();
		response.setId(project.getId());
		response.setTitle(project.getTitle());
		response.setDescription(project.getDescription());
		response.setImages(project.getImages());
		response.setCategory(project.getCategory());
		response.setRoom(project.getRoom());
		response.setAuthor(author);
		return response;
	}

	public LatestProjectResponse dbProjectsToLatestProjectResponse(final List<DBProject> dbProjects) {
		final LatestProjectResponse response = new LatestProjectResponse();
		for(DBProject dbProject : dbProjects) {			
			final LightProject project = new LightProject();
			project.setId(dbProject.getId());
			project.setCreationDate(dbProject.getCreationDate());
			project.setTitle(dbProject.getTitle());
			project.setCategory(dbProject.getCategory());
			project.setRoom(dbProject.getRoom());
			project.setImages(dbProject.getImages().get(0));
			
			response.addProject(project);
		}
		return response;
	}

	public Date fromDateStringToDate(final String fromDateString) throws DHServiceException {
		Date fromDate = null;
		if(fromDateString != null) {
			fromDate = new Date(Long.parseLong(fromDateString));
		}
		return fromDate;
	}
	
}
