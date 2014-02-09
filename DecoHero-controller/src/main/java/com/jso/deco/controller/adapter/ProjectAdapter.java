package com.jso.deco.controller.adapter;

import java.util.List;

import com.jso.deco.api.controller.Author;
import com.jso.deco.api.controller.ProjectResponse;
import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;
import com.jso.deco.data.api.DBUserInfos;

public class ProjectAdapter {

	public DBProject projectCreationRequestToDBProject(ProjectCreationRequest request, List<String> imgIds, String userId) {
		DBProject project = new DBProject();
		project.setTitle(request.getTitle());
		project.setDescription(request.getDescription());
		project.setCategory(request.getCategory().name());
		project.setRoom(request.getRoom() != null ? request.getRoom().name() : null);
		project.getImages().addAll(imgIds);
		project.setUserId(userId);
		return project;
	}

	public ProjectResponse dbProjectToProjectResponse(DBProject project, DBUserInfos authorUser) {
		Author author = new Author();
		author.setId(authorUser.getId());
		author.setUsername(authorUser.getUsername());
		author.setAvatar(authorUser.getAvatar());
		author.setProfessional(authorUser.isProfessionnal());

		ProjectResponse response = new ProjectResponse();
		response.setId(project.getId());
		response.setTitle(project.getTitle());
		response.setDescription(project.getDescription());
		response.setImages(project.getImages());
		response.setCategory(project.getCategory());
		response.setRoom(project.getRoom());
		response.setAuthor(author );
		return response;
	}
	
}
