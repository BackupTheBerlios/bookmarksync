package de.andy.bookmark.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Folder extends Entry {
	
	private String name = "";
	private String id;
	private String description = "";
	private Date lastmodified;
	private Date added;
	private boolean isToolbarFolder;
	
	private List children = new ArrayList();
	
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
	
	public boolean equals(Folder f) {
		return id.equals(f.getId());
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
	
	public void addChild(Entry f) {
		children.add(f);
	}
	
	public Entry[] getChildren() {
		Object[] tmp = children.toArray();
		if (tmp != null) {
			Entry[] ret = new Entry[tmp.length];
			for (int i = 0; i < tmp.length; i++) {
				ret[i] = (Entry) tmp[i];
			}
			return ret;
		}
		return null;
	}
	
	public boolean hasChildren() {
		return (!children.isEmpty());
	}
	
	/*
	 * Ausgehend vom Root-Folder den Folder mit der angegebenen id
	 * suchen und zurückgeben.
	 * Gibt null zurück, falls nichts gefunden.
	 */
	public static  Folder getFolder(Folder root, String id) {
		Folder tmp = getFolderFrom(root,id);
		return tmp;
	}
	
	/*
	 * Rekursive Hilfsmethode für getFolder.
	 */
	private static Folder getFolderFrom(Folder start, String id) {
		Folder tmp_f = null;
		if (start.hasChildren()) {
			Entry[] tmp = start.getChildren();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].getId().equals(id)) return (Folder)tmp[i];
			}
			for (int i = 0; i < tmp.length; i++) {
				if (tmp_f != null) return tmp_f;
				if (tmp[i].getId().equals(id)) tmp_f = getFolderFrom((Folder)tmp[i],id);
			}
		}
		return null;
	}
	
}
