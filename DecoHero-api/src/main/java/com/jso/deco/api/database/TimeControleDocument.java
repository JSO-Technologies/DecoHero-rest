package com.jso.deco.api.database;

import java.util.Date;

public class TimeControleDocument {
	private Date creationDate;
	private Date modificationDate;
	private Date deletionDate;
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getModificationDate() {
		return modificationDate;
	}
	
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
	public Date getDeletionDate() {
		return deletionDate;
	}
	
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
}
