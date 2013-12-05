package com.jso.deco.data.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.jso.deco.api.database.DBUser;

@Repository
public class UserDataService {
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String DELETION_DATE = "deletionDate";

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Create a user in database
	 * @param user
	 */
	public void create(DBUser user) {
		user.setCreationDate(new Date());
		mongoTemplate.save(user);
	}
	
	/**
	 * Update a user in database
	 * @param user
	 */
	public void update(DBUser user) {
		user.setModificationDate(new Date());
		mongoTemplate.save(user);
	}
	
	/**
	 * Delete a user in database
	 * @param user
	 */
	public void delete(DBUser user) {
		user.setDeletionDate(new Date());
		mongoTemplate.save(user);
	}
	
	/**
	 * Find user by id
	 * @param id
	 * @return
	 */
	public DBUser findById(String id) {
		Criteria idCriteria = Criteria.where(ID).is(id);
		Query searchUserQuery = new Query(idCriteria);
		return mongoTemplate.findOne(searchUserQuery, DBUser.class);
	}
	
	/**
	 * Find user from username/encrypted_password
	 * @param username
	 * @param password
	 * @return
	 */
	public DBUser findById(String username, String password) {
		Criteria usernameCriteria = Criteria.where(USERNAME).is(username);
		Criteria passwordCriteria = Criteria.where(PASSWORD).is(password);
		Criteria notDeletedCriteria = Criteria.where(DELETION_DATE).is(null);
		Query searchUserQuery = new Query(usernameCriteria.andOperator(passwordCriteria).andOperator(notDeletedCriteria));
		return mongoTemplate.findOne(searchUserQuery, DBUser.class);
	}
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}
