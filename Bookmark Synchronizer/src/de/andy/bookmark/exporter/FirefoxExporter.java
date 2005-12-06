package de.andy.bookmark.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Folder;
import de.andy.bookmark.importer.FirefoxImporter;

public class FirefoxExporter {
	
	private Folder currentFolder = new Folder("");
	private Stack folderStack = new Stack();
	private int previousFolderDepth = 0;
	private BookmarkCollection bookmarks;

	/*
	 * existing files will be overridden
	 */
	public void exportBookmarks(BookmarkCollection coll, File f)
			throws ExporterException {
		this.bookmarks = coll;
		// check collection
		if (coll == null)
			throw new ExporterException("no collection to export");
		//open file
		FileWriter writer;
		try {
			writer = new FileWriter(f,false); //overrides the file!!!
			BufferedWriter bwriter = new BufferedWriter(writer);
			writePrerequisits(bwriter);
			recuriveWriteFolder(coll.getRootFolder(),bwriter);
			bwriter.close();
			writer.close();
		} catch (IOException e) {
			throw new ExporterException(e.getMessage());
		}
	}
	
	private void recuriveWriteFolder(Folder startFolder, BufferedWriter bwriter) throws IOException {
//		folderstart
		if (!startFolder.equals(bookmarks.getRootFolder())) {
			bwriter.append("<DL><p>");
			bwriter.newLine();
			writeFolder(bwriter, startFolder);
		}
		if (startFolder.hasChildren()) {			
			//jeden folder rekursiv
			for (int i = 0; i < startFolder.getChildren().length; i++) {
				recuriveWriteFolder(startFolder.getChildren()[i],bwriter);
			}			
		}
		if (startFolder.hasBookmarks()) {
			Iterator iterator = startFolder.getBookmarkIterator();
			while (iterator.hasNext()) {
				writeBookmark(bwriter, (Bookmark)iterator.next());
			}
		}
		if (!startFolder.equals(bookmarks.getRootFolder())) {
//		folderend
			bwriter.append("</DL><p>");
			bwriter.newLine();
		}
	}

	/*
	 * writes the head of the file
	 */
	private void writePrerequisits(BufferedWriter bwriter) throws IOException {		
		//doctype
		bwriter.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>");
		bwriter.newLine();
		//notice
		bwriter.append("<!-- This is an automatically generated file.");
		bwriter.newLine();
		bwriter.append("\tIt will be read and overwritten.");
		bwriter.newLine();
		bwriter.append("\tDO NOT EDIT! -->");
		bwriter.newLine();
		//meta data
		bwriter.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
		bwriter.newLine();
		//title
		bwriter.append("<TITLE>Bookmarks</TITLE>");
		bwriter.newLine();
		//last modified
		//convert timestamp to mozilla timestamp 
		long timestamp = System.currentTimeMillis() / 1000;
		bwriter.append("<H1 LAST_MODIFIED=\""+timestamp+"\">Bookmarks</H1>");
		bwriter.newLine();
		bwriter.newLine();
	}
	

	private void writeBookmark(BufferedWriter bwriter, Bookmark bookmark) throws IOException {
		bwriter.append("<DT><A HREF=\""+bookmark.getUrl()+"\""+">"+bookmark.getName()+"</A>");
		bwriter.newLine();
	}
	
	private void writeFolder(BufferedWriter bwriter, Folder folder) throws IOException {
			bwriter.append("<DT><H3 ADD_DATE=\""+(folder.getAdded().getTime()/1000));
			bwriter.append("\" LAST_MODIFIED=\""+(folder.getLastmodified().getTime()/1000)+"\"");
			bwriter.append(" ID=\""+folder.getId()+"\">"+folder.getName()+"</H3>");
			bwriter.newLine();
	//		bwriter.append("<DD>Desc");
//			bwriter.newLine();
	}
	
	//testing only
	public static void main(String[] args) throws Exception {
		new FirefoxExporter().exportBookmarks(new FirefoxImporter().getBookmarks(new File("d:\\bookmarks.html")), new File("d:\\test.bookmark"));
	}

}
