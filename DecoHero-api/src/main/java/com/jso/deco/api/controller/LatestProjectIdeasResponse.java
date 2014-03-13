package com.jso.deco.api.controller;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class LatestProjectIdeasResponse {
	private final List<ProjectIdeaResponse> ideas = newArrayList();

	public List<ProjectIdeaResponse> getIdeas() {
		return ideas;
	}
	
	public void addIdea(final ProjectIdeaResponse idea) {
		ideas.add(idea);
	}
}
