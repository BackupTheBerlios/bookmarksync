package de.andy.bookmark;

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


public class FirefoxImporter {
	
	private static final boolean DEBUG = false;
	private Folder currentFolder;
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
			while (iter.hasMoreNodes()) {
				node = iter.nextNode();
				if (node instanceof DoctypeTag) {
					checkDocType((DoctypeTag)node);
				}
				if ((node instanceof TagNode)
						&& ((TagNode) node).getTagName().equals("H1")) {
					if (!((TagNode) node).isEndTag()) processStart(iter.nextNode());
				} 
				else if ((node instanceof TagNode)
						&& ((TagNode) node).getTagName().equals("DL")) {
					processDepth((TagNode) node);
				} 
				else if ((node instanceof TagNode)
						&& ((TagNode) node).getTagName().equals("H3")) {
					if (!((TagNode)node).isEndTag()) processFolder(iter.nextNode());
				} 
				else if ((node instanceof TagNode)
						&& ((TagNode) node).getTagName().equals("A")) {
					if (!((TagNode)node).isEndTag()) processBookmark(node);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return bookmarks;
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
	
	private void processFolder(Node node) {
		if (node instanceof TextNode) {
			TextNode t = (TextNode)node;
			folderStack.push(currentFolder);
			//FIXME add id from tag to folder
			currentFolder = new Folder(t.getText());
			if (DEBUG) System.out.println(currentFolder);
		}		 
	}

	private void processBookmark(Node node) {
		if (node instanceof TagNode) {
			TagNode t = (TagNode)node;
			String url = t.getAttribute("HREF");
			String name = t.getChildren().asString();
			if (DEBUG) System.out.println(name+" --> "+url+" ");
			Date d_added = getDate(t.getAttribute("ADD_DATE"));
			Date d_lm = getDate(t.getAttribute("LAST_MODIFIED"));
			Bookmark b = new Bookmark(name,url);
			b.setAdded(d_added);
			b.setLastmodified(d_lm);
			b.setDepth(currentDepth);
			b.setFoldername(currentFolder);
			if (folderStack.size() > 0)
				b.setParent((Folder)folderStack.lastElement());
			bookmarks.add(b);
		}
	}

	private Date getDate(String attribute) {
		if (attribute == null) return new Date();
		long timestamp = Long.parseLong(attribute);
		Date d = new Date(timestamp);
		return d;
	}
	
	private void processStart(Node node) {
		if (node instanceof TextNode) {
			TextNode t = (TextNode)node;
			if (DEBUG) System.out.println("Überschrift: "+t.getText());
			bookmarks.setName(t.getText());
		}
	}

	private void checkDocType(DoctypeTag tag) {
		String doc = tag.getText();
		if (doc.contains("NETSCAPE-Bookmark")) {
			if (DEBUG) System.out.println("Should be firefox-bookmark-file.");
		}
		else throw new RuntimeException("Not a firefox-bookmark-file!");
	}
	
	//only for testing
	public static void main(String[] args) {
		FirefoxImporter importer = new FirefoxImporter();
		BookmarkCollection bookmarks = importer.getBookmarks(new File("bookmarks2.html"));
		bookmarks.printAll();
	}
}
	