package de.andy.bookmark.model;

import java.util.Iterator;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.andy.bookmark.Bookmark;
import de.andy.bookmark.BookmarkCollection;

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
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(iter.next());
			findParent(node).add(node);
		}
		imodel.nodeStructureChanged(root);
	}

	private DefaultMutableTreeNode findParent(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode superparent;
		DefaultMutableTreeNode parent = root;
		Bookmark b = (Bookmark)node.getUserObject();
		if (b.getFoldername() == null) return root;
		if (b.getParent() != null) {
			parent = FolderNode.getNodeForFolder(b.getFoldername());
			superparent = FolderNode.getNodeForFolder(b.getParent());
			if (superparent.getIndex(parent) == -1) {
				superparent.add(parent);
			}
		} else {
			parent = FolderNode.getNodeForFolder(b.getFoldername());
			if (root.getIndex(parent) == -1) root.add(parent);
		}
		return parent;
	}
	
}
