package test.protocol.segment;

import ctd.controller.exception.ControllerException;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.container.Segment;
import junit.framework.TestCase;

public class SegmentTester extends TestCase {
	
	public void testSegment1() throws ControllerException{
		Segment s =SegmentController.instance().get("test.protocol.segment.Segment1");
		assertNotNull(s);
		assertEquals(4, s.getChildElementsCount());
		
		Element el = s.getElementAt(0);
		assertEquals("meta.MetaSet1.PN",el.getType());
		assertEquals(1,el.getRepetition());
		assertEquals(true,el.isRequire());
		assertEquals("≤°»À–’√˚",el.getName());
		
	}
	
	public void testSegment2() throws ControllerException{
		Segment s =SegmentController.instance().get("test.protocol.segment.Segment2");
		assertNotNull(s);
		
		assertEquals(5,s.getChildElementsCount());
		assertEquals(4,s.getElementById("birthday").getPosition());
		
		Segment s2 =SegmentController.instance().get("test.protocol.segment.Segment1");
		assertNotNull(s2);
		assertEquals(4, s2.getChildElementsCount());
	}
	
	public void testSegment3() throws ControllerException{
		Segment s =SegmentController.instance().get("test.protocol.segment.Segment3");
		assertNotNull(s);
		
		assertEquals(3,s.getChildElementsCount());
		
		XMLMessage xmlMsg = XMLMessageFactory.createMessage(s);
		xmlMsg.child("email").setValue("sean220@gmail.com");
		xmlMsg.child("idNumber").setValue("332603198002205437");
		
		ValidateStatus status = xmlMsg.validate();
		System.out.println(xmlMsg.getData().asXML());
		System.out.println(status);
		
	}
	

}
