package com.jso.deco.data.service;

import java.util.Date;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.jso.deco.data.api.DBProjectIdea;

@Repository
public class ProjectIdeaDataService {
	private MongoTemplate mongoTemplate;

	/**
	 * Create a project idea in database
	 * @param project
	 */
	public void create(final DBProjectIdea idea) {
		idea.setCreationDate(new Date());
		mongoTemplate.save(idea);
	}
	
	public void setMongoTemplate(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
