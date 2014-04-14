package test.protocol.message;

import java.util.Date;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ctd.controller.exception.ControllerException;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.container.Message;
import ctd.util.context.ContextUtils;
import ctd.util.context.beans.DateBean;
import junit.framework.TestCase;

public class InsMessageTester extends TestCase {
	
	public void testMessage1() throws ControllerException{
		Message schema = (Message)SegmentController.instance().get("test.protocol.message.Message1");
		assertNotNull(schema);
		
		XMLMessage xmlMsg = XMLMessageFactory.createMessage(schema);
		assertNotNull(xmlMsg);
		xmlMsg.setAttribute("versionId", "v201403");
		xmlMsg.child("reportData").setValue(new Date());
		xmlMsg.child(0).child("PT").child("ID").setValue("00234");
		xmlMsg.child(0).child("PT").child("visitTimes").setValue(5);
		
		System.out.println(xmlMsg.child("reportData").getValue());
		
		org.dom4j.Element root = DocumentHelper.createElement("root");
		xmlMsg.appendTo(root);
		
		System.out.println(root.asXML());
		
		XMLMessage xmlMsg2 = XMLMessageFactory.createMessage(schema);
		xmlMsg2.addData((Element) root.elements().get(0));
		
		System.out.println(xmlMsg2.child(0).child("PT").child("ID").getValue());
	}
	
	public void testMessage2() throws ControllerException{
		Message schema = (Message)SegmentController.instance().get("test.protocol.message.Message2");
		assertNotNull(schema);
		
		ContextUtils.put("date", new DateBean());
		XMLMessage xmlMsg = XMLMessageFactory.createMessage(schema);
		assertNotNull(xmlMsg);
		
		xmlMsg.child("item").setValue("AD001");
		xmlMsg.child("item").nextGroup();
		xmlMsg.child("item").setValue("AD002");
		
		org.dom4j.Element root = DocumentHelper.createElement("root");
		xmlMsg.appendTo(root);
		
		System.out.println(root.asXML());
	}
}
