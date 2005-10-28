package de.andy.bookmark.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import de.andy.bookmark.data.Bookmark;
import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.data.Folder;
import de.andy.bookmark.importer.FirefoxImporter;

public class FirefoxExporter {
	
	private Folder currentFolder = null;
	private Folder previousFolder = null;

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
		//TODO insert timestamp
		bwriter.append("<H1 LAST_MODIFIED=\"1122575250\">Bookmarks</H1>");
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
		
		bwriter.append("<DT><A HREF=\""+bookmark.getUrl()+"\""+">"+bookmark.getName()+"</A>");	
	}
	
	
	
	
	//testing only
	public static void main(String[] args) throws Exception {
		new FirefoxExporter().exportBookmarks(new FirefoxImporter().getBookmarks(new File("h:\\bookmarks.html")), new File("d:\\test.bookmark"));
	}

}
