package test.protocol.validate;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.core.io.Resource;

import ctd.controller.exception.ControllerException;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.container.Message;
import ctd.protocol.schema.support.container.Segment;
import ctd.resource.ResourceCenter;
import ctd.util.context.ContextUtils;
import ctd.util.context.beans.DateBean;
import ctd.util.xml.XMLHelper;
import junit.framework.TestCase;

public class ValidateOrderTester extends TestCase {
	private XMLMessage xmlMsg;
	
	
	public ValidateOrderTester() throws ControllerException{
		Message schema = (Message)SegmentController.instance().get("test.protocol.message.Message3");
		assertNotNull(schema);
		
		ContextUtils.put("date", new DateBean());
		xmlMsg = XMLMessageFactory.createMessage(schema);
		assertNotNull(xmlMsg);
	}
	
	public void testOrderValidate() throws ControllerException, IOException, DocumentException{
		Resource r = ResourceCenter.load("test/protocol/validate/message3Data.xml");
		Document doc = XMLHelper.getDocument(r.getInputStream());
		assertNotNull(doc);
		
		Segment s = (Segment)xmlMsg.getElement();
		System.out.println(s.getOrder());
		
		xmlMsg.addData(doc.getRootElement());
		System.out.println(xmlMsg.getData().asXML());
		
		System.out.println(xmlMsg.child("reqDt").getValue());
		
		assertEquals(2, xmlMsg.child("item").getGroupCount());
		
		ValidateStatus status = xmlMsg.validate();
		System.out.println(status);
		
	}
	
	
}
