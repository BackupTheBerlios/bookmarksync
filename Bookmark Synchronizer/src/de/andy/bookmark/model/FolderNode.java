package de.andy.bookmark.model;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import de.andy.bookmark.data.Folder;

/**
 * Pool for DefaultMutableTreeNode with Folders in it.
 * 
 * @author Andreas
 *
 */
public class FolderNode {
	
	private static HashMap hm = new HashMap();

	public static DefaultMutableTreeNode getNodeForFolder(Folder f) {
		if (!hm.containsKey(f)) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
			hm.put(f,node);
		}
		return (DefaultMutableTreeNode) hm.get(f);
	}

}
