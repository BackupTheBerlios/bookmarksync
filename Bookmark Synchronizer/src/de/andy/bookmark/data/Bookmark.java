package de.andy.bookmark.data;

import java.net.URI;
import java.net.URISyntaxException;
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
	
	
	private String name = "";
	private String id;
	private String description = "";
	private String shortcuturl = "";
	private String icon_data = "";
	private URI url;
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
			this.url = new URI(url);
		} catch (URISyntaxException e) {
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
	
	//must be never null!!!
	public Folder getFolder() {
		if (folder != null)
		return folder;
		else return Folder.EMPTY_FOLDER;
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
	public URI getUrl() {
		return url;
	}
	public void setUrl(URI url) {
		this.url = url;
	}
	
	
	public String toString() {
		return name + " --> " + url 
		+ " (" + id +")";
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
	public String getIcon_data() {
		return icon_data;
	}
	public void setIcon_data(String icon_data) {
		this.icon_data = icon_data;
	}
	public String getShortcuturl() {
		return shortcuturl;
	}
	public void setShortcuturl(String shortcuturl) {
		this.shortcuturl = shortcuturl;
	}

}
