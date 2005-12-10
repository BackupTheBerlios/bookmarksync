package de.andy.bookmark.importer;

import java.io.File;
import java.util.Date;
import java.util.Stack;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Folder;

/**
 * This class provides the importing of the bookmarks out of a bookmark 
 * file of Firefox. You should use getBookmarks(File) and you get a 
 * BookmarkCollection.
 * 
 * Attributes not read:
 * - LAST_CHARSET
 * - FAVICON data
 * 
 * @author Andreas
 *
 */
public class FirefoxImporter {
	
	private static final boolean DEBUG = false;
	private static final int NONE = 0;
	private static final int NEW_BOOKMARK = 1;
	private static final int NEW_FOLDER = 2;
	private Folder currentFolder = null;
	private Bookmark currentBookmark = null;
	private Stack folderStack = new Stack();
	private int lastAction = NONE;
	private int currentDepth = -1;
	private BookmarkCollection bookmarks = new BookmarkCollection();

	public BookmarkCollection getBookmarks(File f) throws ImporterException {
		if (DEBUG) System.out.println("Parsing "+f.getName()+": ");
		if (!f.exists()) throw new ImporterException("File not found.");
		if (!f.canRead()) throw new ImporterException("File not readable.");
		bookmarks = new BookmarkCollection();
		currentFolder = bookmarks.getRootFolder();
		try {
			Parser parser = new Parser(f.getAbsolutePath());
			NodeIterator iter = parser.elements();
			Node node;
			//check if bookmark file
			if (iter.hasMoreNodes()) {
				node = iter.nextNode();
				if (node instanceof DoctypeTag) {
					checkDocType((DoctypeTag)node);
				}
				else throw new ImporterException("Not a bookmark file. "+f.getName());
			}
			//read all bookmarks
			while (iter.hasMoreNodes()) {
				node = iter.nextNode();
				if (node instanceof TagNode) {
					TagNode tn = (TagNode)node;
					//Folderstructure
					if (tn.getTagName().equals("DL")) processDepth(tn);
					//Root
					if ((tn.getTagName().equals("H1")) && (!tn.isEndTag()) )
						processStart(tn,iter.nextNode());
					//Folder
					if ((tn.getTagName().equals("H3")) && (!tn.isEndTag()) )
						processFolder(tn,iter.nextNode());
					//single Bookmark
					if ((tn.getTagName().equals("A")) && (!tn.isEndTag()) )
						processBookmark(tn);
					//Description
					if ((tn.getTagName().equals("DD")) && (!tn.isEndTag()) )
						processDesc(tn,iter.nextNode());
				}
			}
		} catch (ParserException e) {
			throw new ImporterException(e.getMessage());
		}
		return bookmarks;
	  }


	private void processDesc(TagNode tn, Node node) {
		if (node instanceof TextNode) {
			String desc = ((TextNode)node).getText();
			switch (lastAction) {
			case NEW_BOOKMARK:
				currentBookmark.setDescription(desc);
				break;
			case NEW_FOLDER:
				currentFolder.setDescription(desc);
				break;
			default:
				break;
			}
			lastAction = NONE;
		}
	}


	private void processDepth(TagNode node) {		
		if (node.isEndTag()) {
			if (!folderStack.isEmpty()) {
				Object obj = folderStack.pop();
				currentFolder = (Folder)obj;
			} else currentFolder = null;
			currentDepth--;
			lastAction = NONE;
		}
		else {
			currentDepth++;
			lastAction = NONE;
		}
	}
	
	private void processFolder(TagNode tn, Node node) {
		if (node instanceof TextNode) {
			TextNode t = (TextNode)node;
			folderStack.push(currentFolder);
			Folder parent = currentFolder;
			String id = tn.getAttribute("ID");
			String toolbar = tn.getAttribute("PERSONAL_TOOLBAR_FOLDER");
			Date d_added = getDate(tn.getAttribute("ADD_DATE"));
			Date d_lm = getDate(tn.getAttribute("LAST_MODIFIED"));
			currentFolder = new Folder(t.getText());
			currentFolder.setId(id);
			currentFolder.setToolbarFolder("true".equalsIgnoreCase(toolbar));
			currentFolder.setAdded(d_added);
			currentFolder.setLastmodified(d_lm);
			parent.addChild(currentFolder);
			if (DEBUG) System.out.println(currentFolder);
			lastAction = NEW_FOLDER;
		}		 
	}

	private void processBookmark(Node node) {
		if (node instanceof TagNode) {
			TagNode t = (TagNode)node;
			String url = t.getAttribute("HREF");
			String name = t.getChildren().asString();
			String id = t.getAttribute("ID");
			String shortcuturl = t.getAttribute("");
			String icon_data = t.getAttribute("ICON");
			if (DEBUG) System.out.println(name+" --> "+url+" ");
			Date d_added = getDate(t.getAttribute("ADD_DATE"));
			Date d_lm = getDate(t.getAttribute("LAST_MODIFIED"));
			Bookmark b = new Bookmark(name,url);
			b.setAdded(d_added);
			b.setLastmodified(d_lm);
			b.setDepth(currentDepth);
			b.setFolder(currentFolder);
			b.setId(id);
			b.setIcon_data(icon_data);
			b.setShortcuturl(shortcuturl);
			if (folderStack.size() > 0)
				b.setParentFolder((Folder)folderStack.lastElement());
			bookmarks.add(b);
			currentBookmark = b;
			lastAction = NEW_BOOKMARK;
		}
	}

	private Date getDate(String attribute) {
		if (attribute == null) return new Date();
		long timestamp = Long.parseLong(attribute);
		//timestamp is without milliseconds!
		Date d = new Date(timestamp*1000);
		return d;
	}
	
	private void processStart(TagNode tn, Node node) {
		if (node instanceof TextNode) {
			TextNode t = (TextNode)node;
			if (DEBUG) System.out.println("Überschrift: "+t.getText());
			bookmarks.setName(t.getText());
		}
	}

	private void checkDocType(DoctypeTag tag) throws ImporterException {
		String doc = tag.getText();
		if (doc.contains("NETSCAPE-Bookmark")) {
			if (DEBUG) System.out.println("Should be firefox-bookmark-file.");
		}
		else throw new ImporterException("Not a firefox-bookmark-file!");
	}
	
}
	