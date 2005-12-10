package de.andy.bookmark.data;


public class BookmarkCollection {
	
	private String name;
	private Folder root = new Folder("!!!ROOT!!! $%&�$%�&$%�");
	
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
		return root.hasChildren();
	}

	public boolean contains(Object o) {
		return false;
	}

	//an die richtige Stelle im Baum einf�gen
	public void add(Bookmark b) {
		Folder f = b.getFolder();
		if (f != null) f.addChild(b);
	}
	
	public void addFolder(Folder parent, Folder child) {
		parent.addChild(child);
	}
	
	//getDupps
	//removeDupps
	//sort

}
