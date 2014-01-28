package com.jso.deco.data.service;

import java.util.Date;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.jso.deco.data.api.DBProject;

@Repository
public class ProjectDataService {
	private MongoTemplate mongoTemplate;

	/**
	 * Create a project in database
	 * @param project
	 */
	public void create(DBProject project) {
		project.setCreationDate(new Date());
		mongoTemplate.save(project);
	}
	
	/**
	 * Delete a project in database
	 * @param project
	 */
	public void delete(DBProject project) {
		project.setDeletionDate(new Date());
		mongoTemplate.save(project);
	}
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
