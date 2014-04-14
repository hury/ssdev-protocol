package test.protocol.message;

import ctd.controller.exception.ControllerException;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.container.Dataset;
import ctd.protocol.schema.support.container.Message;
import ctd.protocol.schema.support.container.Segment;
import junit.framework.TestCase;

public class MessageTester extends TestCase {
	public void testMessage1() throws ControllerException{
		Message msg = (Message)SegmentController.instance().get("test.protocol.message.Message1");
		assertNotNull(msg);
		
		Dataset ds = (Dataset) msg.getElementById("RPT");
		assertNotNull(ds);
		
		assertEquals("°æ±¾ºÅ", ds.getAttribute("versionId").getName());
		
		Segment segment = (Segment) ds.getElementAt(0); 
		assertEquals("PN",segment.getElementAt(0).getId());
		
		assertEquals(5,segment.getChildElementsCount());
		assertEquals("visitTimes",segment.getElementAt(4).getId());
		
		assertTrue(segment.getElementAt(2).isRequire());
		assertTrue(segment.getElementById("ID").isRequire());
	}
}
