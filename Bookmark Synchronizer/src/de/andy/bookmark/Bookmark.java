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
	private URL url;
	private Date lastmodified;
	private Date added;
	private Folder foldername;
	private Folder parent;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Folder getFoldername() {
		return foldername;
	}
	public void setFoldername(Folder currentFolder) {
		this.foldername = currentFolder;
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
		return name + " --> " + url.toExternalForm();
	}
	public Folder getParent() {
		return parent;
	}
	public void setParent(Folder parent) {
		this.parent = parent;
	}
	
	public void testStatus() {
		//
		setCheckflag(Bookmark.OK);
	}

}
