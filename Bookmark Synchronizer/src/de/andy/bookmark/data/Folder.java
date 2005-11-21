package de.andy.bookmark.data;

import java.util.Date;
import java.util.UUID;

public class Folder {
	
	private String name = "";
	private String id;
	private String description = "";
	private Date lastmodified;
	private Date added;
	private boolean isToolbarFolder;
	
	public static Folder EMPTY_FOLDER = new Folder("nope","!!!EMPTY_FOLDER!!!");
	
	public Folder(String id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public Folder(String name) {
		this.name = name;
		id = generateId();
	}
	
	private String generateId() {		
		return UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isToolbarFolder() {
		return isToolbarFolder;
	}
	public void setToolbarFolder(boolean isToolbarFolder) {
		this.isToolbarFolder = isToolbarFolder;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	
}
