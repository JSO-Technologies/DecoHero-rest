package com.jso.deco.controller.adapter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jso.deco.api.controller.Author;
import com.jso.deco.api.controller.LatestProjectIdeasResponse;
import com.jso.deco.api.controller.LatestProjectResponse;
import com.jso.deco.api.controller.LightProject;
import com.jso.deco.api.controller.ProjectIdeaResponse;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.exception.DHServiceException;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.api.service.request.ProjectIdeaCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBProjectIdea;
import com.jso.deco.data.api.DBUserInfos;

public class ProjectAdapter {

	public DBProject projectCreationRequestToDBProject(final ProjectCreationRequest request, final Set<String> imgIds, final String userId) {
		final DBProject project = new DBProject();
		project.setTitle(request.getTitle());
		project.setDescription(request.getDescription());
		project.setCategory(request.getCategory().name());
		project.setRoom(request.getRoom() != null ? request.getRoom().name() : null);
		project.getImages().addAll(imgIds);
		project.setUserId(userId);
		return project;
	}

	public ProjectResponse dbProjectToProjectResponse(final DBProject project, final DBUserInfos authorUser) {
		final ProjectResponse response = new ProjectResponse();
		response.setId(project.getId());
		response.setTitle(project.getTitle());
		response.setDescription(project.getDescription());
		response.setImages(project.getImages());
		response.setCategory(project.getCategory());
		response.setRoom(project.getRoom());
		response.setAuthor(dbUserInfosToAuthor(authorUser));
		return response;
	}

	private Author dbUserInfosToAuthor(DBUserInfos authorUser) {
		final Author author = new Author();
		author.setId(authorUser.getId());
		author.setUsername(authorUser.getUsername());
		author.setAvatar(authorUser.getAvatar());
		author.setProfessional(authorUser.isProfessionnal());
		
		return author;
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

	public DBProjectIdea projectIdeaCreationRequestToDBProjectIdea(final String userId, final String projectId, final ProjectIdeaCreationRequest request, final Set<String> imgIds) {
		final DBProjectIdea idea = new DBProjectIdea();
		idea.setUserId(userId);
		idea.setProjectId(projectId);
		idea.setDescription(request.getDescription());
		idea.getImages().addAll(imgIds);
		
		return idea;
	}

	public ProjectIdeaResponse dbProjectIdeaToCreateProjectIdeaResponse(final DBUserInfos user, final DBProjectIdea idea) {
		final ProjectIdeaResponse response = new ProjectIdeaResponse();
		response.setId(idea.getId());
		response.setAuthor(dbUserInfosToAuthor(user));
		response.setDescription(idea.getDescription());
		response.getImages().addAll(idea.getImages());
		response.setCreationDate(idea.getCreationDate());
		
		return response;
	}

	public LatestProjectIdeasResponse dbProjectIdeasToLatestProjectIdeasResponse(final List<DBProjectIdea> dbProjectIdeas, final Map<String, DBUserInfos> users) {
		final LatestProjectIdeasResponse response = new LatestProjectIdeasResponse();
		for(DBProjectIdea idea : dbProjectIdeas) {
			response.addIdea(dbProjectIdeaToCreateProjectIdeaResponse(users.get(idea.getUserId()), idea));
		}
		
		return response;
	}
	
}
