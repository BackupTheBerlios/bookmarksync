package de.andy.bookmark.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.andy.bookmark.Bookmark;

public class BookmarkTreeCellRenderer extends DefaultTreeCellRenderer {

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
			if (node.getUserObject() instanceof Bookmark) {
				Bookmark b = (Bookmark)node.getUserObject();
				switch (b.getCheckflag()) {
					case Bookmark.OK:
						setLabelColor(c,Color.green);
						break;
					case Bookmark.UNCHECKED:
						setLabelColor(c,Color.yellow);
						break;
					case Bookmark.UNREACHABLE:
						setLabelColor(c,Color.red);
						break;
					default:
						setLabelColor(c,Color.black);
				}
			}
		}
		return c;
	}

	private void setLabelColor(Component c, Color col) {
		if (c instanceof JLabel) {
			JLabel l = (JLabel)c;
			l.setForeground(col);
		}
		
	}

}
