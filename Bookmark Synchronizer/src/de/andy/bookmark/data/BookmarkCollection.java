package de.andy.bookmark.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BookmarkCollection {
	
	private String name;
	private Folder root = new Folder("!!!ROOT!!! $%&§$%§&$%§");
	
	public Folder getRootFolder() {
		return root;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}

	public boolean isEmpty() {
		return root.hasBookmarks() || root.hasChildren();
	}

	public boolean contains(Object o) {
		return false;
	}

	//an die richtige Stelle im Baum einfügen
	public void add(Bookmark b) {
		Folder f = b.getFolder();
		f.addBookmark(b);
	}
	
	public void addFolder(Folder parent, Folder child) {
		parent.addChildFolder(child);
	}
	
	//getDupps
	//removeDupps
	//sort

}
