package de.andy.bookmark.model;

import java.util.Iterator;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Folder;

public class BookmarkTreeModel implements TreeModel {
	
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Bookmarks");
	private DefaultTreeModel imodel = new DefaultTreeModel(root);

	public Object getRoot() {
		return imodel.getRoot();
	}

	public Object getChild(Object parent, int index) {
		return imodel.getChild(parent,index);
	}

	public int getChildCount(Object parent) {
		return imodel.getChildCount(parent);
	}

	public boolean isLeaf(Object node) {
		return imodel.isLeaf(node);
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		imodel.valueForPathChanged(path,newValue);		
	}

	public int getIndexOfChild(Object parent, Object child) {
		return imodel.getIndexOfChild(parent,child);
	}

	public void addTreeModelListener(TreeModelListener l) {
		imodel.addTreeModelListener(l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		imodel.addTreeModelListener(l);
	}
	
	//--------------
	
	public void setBookmarkCollection(BookmarkCollection coll) {
		root.removeAllChildren();
		//add Bookmarks
		if (coll != null) {
			//add folders and bookmarks recursively
//			addBookmarksAndFolders(root,coll.getRootFolder());
		} else root.setUserObject("No Bookmarks here");
		imodel.nodeStructureChanged(root);
	}
	
	/*
	 * Rekursive Hilfsfunktion für setBookmarkCollection()
	 */
//	private void addBookmarksAndFolders(DefaultMutableTreeNode start, Folder f) {
//		if (f.hasChildren()) {
//			for (int i = 0; i < f.getChildren().length; i++) {
//				DefaultMutableTreeNode n = new DefaultMutableTreeNode(f.getChildren()[i]);
//				start.add(n);
//				addBookmarksAndFolders(n,f.getChildren()[i]);
//			}
//		}
//		if (f.hasBookmarks()) {
//			Iterator iter = f.getBookmarkIterator();
//			while (iter.hasNext()) {
//				start.add(new DefaultMutableTreeNode(iter.next()));
//			}
//		}
//	}

	private DefaultMutableTreeNode findParent(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode superparent;
		DefaultMutableTreeNode parent = root;
		Bookmark b = (Bookmark)node.getUserObject();
		if (b.getFolder() == null) return root;
		if (b.getParentFolder() != null) {
			parent = FolderNode.getNodeForFolder(b.getFolder());
			superparent = FolderNode.getNodeForFolder(b.getParentFolder());
			if (superparent.getIndex(parent) == -1) {
				superparent.add(parent);
			}
		} else {
			parent = FolderNode.getNodeForFolder(b.getFolder());
			if (root.getIndex(parent) == -1) root.add(parent);
		}
		return parent;
	}
	
}
