package com.jso.deco.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
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
	public void create(final DBProject project) {
		project.setCreationDate(new Date());
		mongoTemplate.save(project);
	}
	
	/**
	 * Find a project from id
	 * @param projectId
	 */
	public DBProject find(final String projectId) {
		final Criteria idCriteria = Criteria.where("id").is(projectId);
		final Query searchProjectQuery = new Query(idCriteria);
		return mongoTemplate.findOne(searchProjectQuery, DBProject.class);
	}
	
	/**
	 * Find latest projects with user id
	 * @param userId
	 * @param limit
	 * @param fromCreationDate - filter creation date &lt; fromCreationDate
	 * @return
	 */
	public List<DBProject> findUserLatest(final String userId, final int limit, final Date fromCreationDate) {
		final Criteria userIdCriteria = Criteria.where("userId").is(userId);
		final Sort sort = new Sort(Sort.Direction.DESC, "creationDate");
		
		final Query searchProjectQuery = new Query(userIdCriteria);
		searchProjectQuery.with(sort);
		searchProjectQuery.limit(limit);
		if(fromCreationDate != null) {
			final Criteria fromDateCriteria = Criteria.where("creationDate").lt(fromCreationDate);
			searchProjectQuery.addCriteria(fromDateCriteria);
		}
		
		return mongoTemplate.find(searchProjectQuery, DBProject.class);
	}
	
	/**
	 * Delete a project in database
	 * @param project
	 */
	public void delete(final DBProject project) {
		project.setDeletionDate(new Date());
		mongoTemplate.save(project);
	}
	
	/**
	 * Check if project exists
	 * @param projectId
	 * @return
	 */
	public boolean exists(String projectId) {
		final Criteria idCriteria = Criteria.where("id").is(projectId);
		final Query query = new Query(idCriteria);
		return mongoTemplate.exists(query, DBProject.class);
	}
	
	/**
	 * Count user projects
	 * @param userId
	 * @return
	 */
	public long countUserProjects(final String userId) {
		final Criteria userCriteria = Criteria.where("userId").is(userId);
		final Query query = new Query(userCriteria );
		return mongoTemplate.count(query, DBProject.class);
	}
	
	public void setMongoTemplate(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
