package test.protocol;

import test.protocol.dataset.DatasetTester;
import test.protocol.message.MessageTester;
import test.protocol.meta.MetaDictionaryTester;
import test.protocol.segment.SegmentTester;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(MetaDictionaryTester.class);
		suite.addTestSuite(SegmentTester.class);
		suite.addTestSuite(DatasetTester.class);
		suite.addTestSuite(MessageTester.class);
		//$JUnit-END$
		//test
		return suite;
	}

}
