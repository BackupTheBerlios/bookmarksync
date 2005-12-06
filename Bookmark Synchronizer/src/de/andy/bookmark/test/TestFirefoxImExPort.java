package de.andy.bookmark.test;

import java.io.File;

import de.andy.bookmark.data.BookmarkCollection;
import de.andy.bookmark.exporter.FirefoxExporter;
import de.andy.bookmark.importer.FirefoxImporter;
import junit.framework.TestCase;

public class TestFirefoxImExPort extends TestCase {
	
	static final String package_path = "src/de/andy/bookmark/test/";
	static final String bookmarktestinfile = "bookmarks.html";
	static final String bookmarktestoutfile = "bookmarks_out.html";
	
	File infile,outfile;
	FirefoxImporter im;
	FirefoxExporter ex;
	BookmarkCollection coll;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		infile = new File(package_path+bookmarktestinfile);
		outfile = new File(package_path+bookmarktestoutfile);
		im = new FirefoxImporter();
		ex = new FirefoxExporter();
	}



	public void testImport() throws Exception {
		coll = null;
		coll = im.getBookmarks(infile);
		assertNotNull(coll);		
	}
	
	public void testExport() throws Exception {
		coll = null;
		coll = im.getBookmarks(infile);//tested in testImport()
		ex.exportBookmarks(coll, outfile);
	}
	
	public void testReImport() throws Exception {
		coll = null;
		coll = im.getBookmarks(outfile);
		assertNotNull(coll);
	}

}
