package com.jso.deco.data.service;

import java.util.Date;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	 * Find a project from id
	 * @param projectId
	 */
	public DBProject find(String projectId) {
		Criteria idCriteria = Criteria.where("id").is(projectId);
		Query searchProjectQuery = new Query(idCriteria);
		return mongoTemplate.findOne(searchProjectQuery, DBProject.class);
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
