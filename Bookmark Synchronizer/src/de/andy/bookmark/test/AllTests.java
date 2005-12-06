package de.andy.bookmark.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for de.andy.bookmark.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestFirefoxImExPort.class);
		//$JUnit-END$
		return suite;
	}

}
