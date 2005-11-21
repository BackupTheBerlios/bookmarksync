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
	private int previousFolderDepth = Integer.MAX_VALUE;

	/*
	 * existing files will be overridden
	 */
	public void exportBookmarks(BookmarkCollection coll, File f)
			throws ExporterException {
		// check collection
		if (coll == null)
			throw new ExporterException("no collection to export");
		//open file
		FileWriter writer;
		try {
			writer = new FileWriter(f,false); //overrides the file!!!
			BufferedWriter bwriter = new BufferedWriter(writer);
			writePrerequisits(bwriter);
			folderStack.push(Folder.EMPTY_FOLDER);
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				Object o = iter.next();				
				writeItem(bwriter, (Bookmark)o);
				bwriter.newLine();
			}			
//			writer.close();
			bwriter.append("</DL><p>");
			bwriter.close();
		} catch (IOException e) {
			throw new ExporterException(e.getMessage());
		}			
		System.out.println("End.");
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
		bwriter.append("<DL><p>");
		bwriter.newLine();
	}
	
	/*
	 * write one Item
	 * can be a single bookmark or a folder
	 */
	private void writeItem(BufferedWriter bwriter, Bookmark bookmark) throws IOException {
		if (!bookmark.getFolder().equals(folderStack.peek())) {
			if (previousFolderDepth < bookmark.getDepth()) {
				writeFolderEnd(bwriter,1);
				folderStack.pop();
				System.out.println("f end.");
			}
			writeFolder(bwriter, bookmark.getFolder());
			System.out.println(bookmark.getFolder().getName()+"-->"+bookmark.getDepth());
			folderStack.push(bookmark.getFolder());
		}
		bwriter.append("<DT><A HREF=\""+bookmark.getUrl()+"\""+">"+bookmark.getName()+"</A>");
		previousFolderDepth = bookmark.getDepth();
	}
	
	//TODO add depth...!
	private void writeFolderEnd(BufferedWriter bwriter, int depth) throws IOException {
		if (!currentFolder.equals(Folder.EMPTY_FOLDER)) {
			for (int i = 1; i <= depth; i++) {
				bwriter.append("</DL><p>");//Folder end
				bwriter.newLine();
			}
		}
	}
	
	
	private void writeFolder(BufferedWriter bwriter, Folder folder) throws IOException {
		if (!folder.equals(Folder.EMPTY_FOLDER)) {
			bwriter.append("<DT><H3 ADD_DATE=\""+(folder.getAdded().getTime()/1000));
			bwriter.append("\" LAST_MODIFIED=\""+(folder.getLastmodified().getTime()/1000)+"\"");
			bwriter.append(" ID=\""+folder.getId()+"\">"+folder.getName()+"</H3>");
			bwriter.newLine();
	//		bwriter.append("<DD>Desc");
	//		bwriter.newLine();
			bwriter.append("<DL><p>");
			bwriter.newLine();
		}
	}
	
	//testing only
	public static void main(String[] args) throws Exception {
		new FirefoxExporter().exportBookmarks(new FirefoxImporter().getBookmarks(new File("d:\\bookmarks.html")), new File("d:\\test.bookmark"));
	}

}
