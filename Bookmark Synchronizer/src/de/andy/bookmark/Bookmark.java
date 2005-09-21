package de.andy.bookmark;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Repräsentation eines Bookmarks.
 * 
 * @author Andreas
 *
 */
public class Bookmark {
	
	//Flags
	public static final int UNCHECKED = 0;
	public static final int OK = 1;
	public static final int UNREACHABLE = 2;
	public static final int UNKNOWN = 3;
	//....................
	
	
	private String name;
	private String id;
	private String description;
	private URL url;
	private Date lastmodified;
	private Date added;
	private Folder folder;
	private Folder parentFolder;	
	private int depth = 0;
	private int checkflag = UNCHECKED;
//	private byte[] data;
	
	public int getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(int checkflag) {
		this.checkflag = checkflag;
	}
	public Bookmark(String name, String url) {
		this.name = name;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			//
		}
	}
	public Date getAdded() {
		return added;
	}
	public void setAdded(Date added) {
		this.added = added;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder currentFolder) {
		this.folder = currentFolder;
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
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	
	public String toString() {
		return name + " --> " + url.toExternalForm() 
		+ "(" + id +")";
	}
	public Folder getParentFolder() {
		return parentFolder;
	}
	public void setParentFolder(Folder parent) {
		this.parentFolder = parent;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void testStatus() {
		//
		setCheckflag(Bookmark.OK);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
