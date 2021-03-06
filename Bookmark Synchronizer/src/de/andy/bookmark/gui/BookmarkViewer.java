package de.andy.bookmark.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Folder;
import de.andy.bookmark.importer.FirefoxImporter;
import de.andy.bookmark.importer.ImporterException;
import de.andy.bookmark.model.BookmarkTreeModel;

public class BookmarkViewer extends JFrame implements TreeSelectionListener {
	
	File current_file = null; //current Bookmarkfile
	BookmarkTreeModel model = new BookmarkTreeModel();
	BookmarkCollection bookmarks = null;
	
	JPanel p_north;
	JPanel p_center;
	JPanel p_south;
	JTextField txt_file;
	JButton b_refresh;
	JButton b_file;
	JButton b_end;
	JButton b_remove;
	JTree l_bookmarks;
	JScrollPane scrollpane;
	JFileChooser chooser = new JFileChooser(new File("D:\\eclipse-work3.1"));

	public BookmarkViewer() {
		super("Simple Bookmark Viewer");
		_initComponents();
		_initActions();
		refreshGUI();
		setLocation(100,100);
		pack();
		setVisible(true);
	}
	
	private void _initActions() {
		b_file.setAction(new AbstractAction("Choose...") {
			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showOpenDialog(BookmarkViewer.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					current_file = chooser.getSelectedFile();
					refreshGUI();
				}
			}
			
		});
		
		b_refresh.setAction(new AbstractAction("Test") {
			public void actionPerformed(ActionEvent e) {
				checkAllBookmarks(model);
			}});
		
		b_remove.setAction(new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
//				refreshGUI();
				
			}});
		
		b_end.setAction(new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				BookmarkViewer.this.dispose();
			}});
	}

	protected void checkAllBookmarks(BookmarkTreeModel model) {
		new Thread(new Runnable(){

			public void run() {
				//
			}}).start();
	}

	private void _initComponents() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		txt_file = new JTextField(35);
		txt_file.setEditable(false);
		b_refresh = new JButton();
		b_file = new JButton();
		b_end = new JButton();
		b_remove = new JButton();
		l_bookmarks = new JTree(model);
//		l_bookmarks.setCellRenderer(new BookmarkTreeCellRenderer());
		l_bookmarks.addTreeSelectionListener(this);
		p_north.add(txt_file);
		p_north.add(b_file);
		scrollpane = new JScrollPane(l_bookmarks);
//		scrollpane.setColumnHeaderView(new JLabel("Bookmarks"));
		p_center.setLayout(new BorderLayout());
		p_center.add(BorderLayout.CENTER, scrollpane);
//		p_south.add(b_refresh);
		p_south.add(b_end);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(BorderLayout.NORTH,p_north);
		this.getContentPane().add(BorderLayout.CENTER,p_center);
		this.getContentPane().add(BorderLayout.SOUTH,p_south);
	}
	
	private void refreshGUI() {
		if (current_file != null) {
			txt_file.setText(current_file.getAbsolutePath());
			new Thread(new Runnable() {
				public void run() {			
					try {
						bookmarks = new FirefoxImporter().getBookmarks(current_file);
						model.setBookmarkCollection(bookmarks);
						l_bookmarks.invalidate();
					} catch (ImporterException e) {
						e.printStackTrace();
						model.setBookmarkCollection(null);
					}
				}}).start();
		}
		else {
			txt_file.setText("*** no file selected ***");
		}
	}

	public static void main(String[] args) {
		new BookmarkViewer();
	}
	
	
	public void valueChanged(TreeSelectionEvent e) {
		try {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) l_bookmarks.getSelectionPath().getLastPathComponent();
		if (node.getUserObject() instanceof Bookmark)
			showBookmarkDetails((Bookmark)node.getUserObject());
		if (node.getUserObject() instanceof Folder)
			showFolderDetails((Folder)node.getUserObject());
		}
		catch (Exception ex) {
			//nothing to do here yet
		}
	}

	private void showFolderDetails(Folder folder) {
		System.out.println("--- Folder: "+folder.getName()+" ---");		
		System.out.println("ID: "+folder.getId());
//		System.out.println("Added: "+folder.getAdded());
//		System.out.println("Lastmodified: "+folder.getLastmodified());
		System.out.println("Description: "+folder.getDescription());
		System.out.println("--- E N D ---");
	}

	private void showBookmarkDetails(Bookmark bookmark) {
		System.out.println("--- Bookmark: "+bookmark.getName()+" ---");
		System.out.println("Url: "+bookmark.getUrl());		
		System.out.println("ID: "+bookmark.getId());
		System.out.println("Added: "+bookmark.getAdded());
		System.out.println("Lastmodified: "+bookmark.getLastmodified());
		System.out.println("Description: "+bookmark.getDescription());
		System.out.println("--- E N D ---");
	}
	
	
}
