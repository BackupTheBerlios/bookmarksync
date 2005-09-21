package de.andy.bookmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Stack;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;


public class FirefoxImporter {
	
	private static final boolean DEBUG = false;
	private Folder currentFolder = null;
	private Bookmark currentBookmark = null;
	private Stack folderStack = new Stack();
	private int currentDepth = -1;
	private BookmarkCollection bookmarks = new BookmarkCollection();

	public BookmarkCollection getBookmarks(File f) {
		if (DEBUG) System.out.println("Parsing "+f.getName()+": ");
		bookmarks = new BookmarkCollection();
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
				else throw new ImporterException("Not a bookmark file.");
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
			e.printStackTrace();
		} catch (ImporterException e) {
			e.printStackTrace();
		}
		return bookmarks;
	  }


	private void processDesc(TagNode tn, Node node) {
		//		
	}


	private void processDepth(TagNode node) {		
		if (node.isEndTag()) {
			if (!folderStack.isEmpty()) {
				Object obj = folderStack.pop();
				currentFolder = (Folder)obj;
			} else currentFolder = null;
			currentDepth--;
		}
		else {
			currentDepth++;
		}
	}
	
	private void processFolder(TagNode tn, Node node) {
		if (node instanceof TextNode) {
			TextNode t = (TextNode)node;
			folderStack.push(currentFolder);
			String id = tn.getAttribute("ID");
			String toolbar = tn.getAttribute("PERSONAL_TOOLBAR_FOLDER");
			currentFolder = new Folder(t.getText());
			currentFolder.setId(id);
			currentFolder.setToolbarFolder("true".equalsIgnoreCase(toolbar));
			if (DEBUG) System.out.println(currentFolder);
		}		 
	}

	private void processBookmark(Node node) {
		if (node instanceof TagNode) {
			TagNode t = (TagNode)node;
			String url = t.getAttribute("HREF");
			String name = t.getChildren().asString();
			String id = t.getAttribute("ID");
			if (DEBUG) System.out.println(name+" --> "+url+" ");
			Date d_added = getDate(t.getAttribute("ADD_DATE"));
			Date d_lm = getDate(t.getAttribute("LAST_MODIFIED"));
			Bookmark b = new Bookmark(name,url);
			b.setAdded(d_added);
			b.setLastmodified(d_lm);
			b.setDepth(currentDepth);
			b.setFolder(currentFolder);
			b.setId(id);
			if (folderStack.size() > 0)
				b.setParentFolder((Folder)folderStack.lastElement());
			bookmarks.add(b);
		}
	}

	private Date getDate(String attribute) {
		if (attribute == null) return new Date();
		long timestamp = Long.parseLong(attribute);
		Date d = new Date(timestamp);
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
	
	//only for testing
	public static void main(String[] args) {
		FirefoxImporter importer = new FirefoxImporter();
		BookmarkCollection bookmarks = importer.getBookmarks(new File("bookmarks2.html"));
		bookmarks.printAll();
	}
}
	