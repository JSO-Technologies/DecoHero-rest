package com.jso.deco.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	
	/**
	 * Find latest projects with project id
	 * @param projectId
	 * @param limit
	 * @param fromCreationDate
	 * @return
	 */
	public List<DBProjectIdea> findProjectLatest(final String projectId, final int limit, final Date fromCreationDate) {
		final Criteria projectIdCriteria = Criteria.where("projectId").is(projectId);
		final Sort sort = new Sort(Sort.Direction.DESC, "creationDate");
		
		final Query searchProjectIdeaQuery = new Query(projectIdCriteria);
		searchProjectIdeaQuery.with(sort);
		searchProjectIdeaQuery.limit(limit);
		if(fromCreationDate != null) {
			final Criteria fromDateCriteria = Criteria.where("creationDate").lt(fromCreationDate);
			searchProjectIdeaQuery.addCriteria(fromDateCriteria);
		}
		
		return mongoTemplate.find(searchProjectIdeaQuery, DBProjectIdea.class);
	}
	
	public void setMongoTemplate(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
