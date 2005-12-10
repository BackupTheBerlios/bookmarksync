package de.andy.bookmark.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Entry;
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
		FileOutputStream fileout;
		try {			
			fileout = new FileOutputStream(f,false); //overrides the file!!!
			OutputStreamWriter ow = new OutputStreamWriter(fileout, "UTF-8"); 
			BufferedWriter buffwriter = new BufferedWriter(ow);
			writePrerequisits(buffwriter);
			recuriveWriteFolder(coll.getRootFolder(),buffwriter);
			buffwriter.close();
			fileout.close();
		} catch (IOException e) {
			throw new ExporterException(e.getMessage());
		}
	}
	
	private void recuriveWriteFolder(Folder startFolder, BufferedWriter bwriter) throws IOException {
		//folderstart
		bwriter.append("<DL><p>");
		bwriter.newLine();
		if (!startFolder.equals(bookmarks.getRootFolder())) writeFolder(bwriter, startFolder);
		Entry[] children = startFolder.getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof Folder) {
					//Folder goes here
					//jeden folder rekursiv					
						recuriveWriteFolder((Folder)children[i],bwriter);
					
				}
				else if (children[i] instanceof Bookmark) {
					//Bookmark goes here
					writeBookmark(bwriter, (Bookmark)children[i]);
				}
			}
		}	
//		folderend
		bwriter.append("</DL><p>");
		bwriter.newLine();
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
