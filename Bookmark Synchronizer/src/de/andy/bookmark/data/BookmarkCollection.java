package de.andy.bookmark.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BookmarkCollection {
	
	private String name;
	private Folder root = Folder.ROOT_FOLDER;
	
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

	//my
	public boolean add(Bookmark b) {
		return false;
	}
	
	//getDupps
	//removeDupps
	//sort

}
