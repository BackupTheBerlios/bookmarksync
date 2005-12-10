package de.andy.bookmark.data;

import java.util.Date;
import java.util.UUID;
/**
 * Oberklasse für Bookmark und Folder.
 * 
 * @author Andreas
 *
 */
public abstract class Entry {	
		
		private String name = "";
		private String id;
		private String description = "";
		private Date lastmodified;
		private Date added;
		
		public Entry() {
			this.id = UUID.randomUUID().toString();
		}
		public Entry(String name) {
			this.name = name;
			this.id = UUID.randomUUID().toString();
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
			
		public String toString() {
			return name +" (" + id +")";
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getId() {
			return id;
		}

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
}
