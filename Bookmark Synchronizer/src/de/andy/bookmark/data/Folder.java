package de.andy.bookmark.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Folder {
	
	private String name = "";
	private String id;
	private String description = "";
	private Date lastmodified;
	private Date added;
	private boolean isToolbarFolder;
	
	private List children = new ArrayList();
	private List bookmarks = new ArrayList(50);
	
	public static Folder ROOT_FOLDER = new Folder("nope","!!!ROOT!!!");
	
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
	
	public void addChildFolder(Folder f) {
		children.add(f);
	}
	
	public Folder[] getChildren() {
		return (Folder[])children.toArray();
	}
	
	public boolean hasChildren() {
		return children.isEmpty();
	}
	
	public boolean hasBookmarks() {
		return bookmarks.isEmpty();
	}
	
	/*
	 * Ausgehend vom Root-Folder den Folder mit der angegebenen id
	 * suchen und zurückgeben.
	 * Gibt null zurück, falls nichts gefunden.
	 */
	public static  Folder getFolder(String id) {
		return null;
	}
	
	/*
	 * Fügt dem aktuelle Folder ein Bookmark hinzu.
	 */
	public void addBookmark(Bookmark b) {
		bookmarks.add(b);
	}
	
	public Bookmark[] getBookmarks() {
		return (Bookmark[])bookmarks.toArray();
	}
	
}
