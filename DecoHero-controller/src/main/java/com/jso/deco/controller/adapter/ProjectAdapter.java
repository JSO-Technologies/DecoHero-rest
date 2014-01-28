package com.jso.deco.controller.adapter;

import java.util.List;

import com.jso.deco.api.service.request.ProjectCreationRequest;
import com.jso.deco.data.api.DBProject;


public class ProjectAdapter {

	public DBProject projectCreationRequestToDBProject(ProjectCreationRequest request, List<String> imgIds) {
		DBProject project = new DBProject();
		project.setTitle(request.getTitle());
		project.setDescription(request.getDescription());
		project.setCategory(request.getCategory().name());
		project.setRoom(request.getRoom() != null ? request.getRoom().name() : null);
		project.getImages().addAll(imgIds);
		return project;
	}
	
}
