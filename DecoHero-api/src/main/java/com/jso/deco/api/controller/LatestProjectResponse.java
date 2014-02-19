package com.jso.deco.api.controller;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class LatestProjectResponse {
	private final List<LightProject> projects = newArrayList();

	public List<LightProject> getProjects() {
		return projects;
	}
	
	public void addProject(LightProject project) {
		projects.add(project);
	}
	
}
