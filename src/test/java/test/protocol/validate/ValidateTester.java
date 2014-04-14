package test.protocol.validate;

import ctd.controller.exception.ControllerException;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.container.Message;
import ctd.util.context.ContextUtils;
import ctd.util.context.beans.DateBean;
import junit.framework.TestCase;

public class ValidateTester extends TestCase {
	private XMLMessage xmlMsg;
	
	
	public ValidateTester() throws ControllerException{
		Message schema = (Message)SegmentController.instance().get("test.protocol.message.Message2");
		assertNotNull(schema);
		
		ContextUtils.put("date", new DateBean());
		xmlMsg = XMLMessageFactory.createMessage(schema);
		assertNotNull(xmlMsg);
	}
	
	public void testValidate1() throws ControllerException{
		
		ValidateStatus status = xmlMsg.validate();
		System.out.println(status);

		
		xmlMsg.setAttribute("versionId", "ver3045");
		status = xmlMsg.validate();
		System.out.println(status);
		
		
		xmlMsg.child("pid").setValue("454");
		
		status = xmlMsg.validate();
		System.out.println(status);
		
		xmlMsg.child("item").setAttribute("id", "009");
		xmlMsg.child("item").setValue("血常规");
		
		xmlMsg.child("item").nextGroup();
		xmlMsg.child("item").setAttribute("id", "012");
		xmlMsg.child("item").setValue("尿常规");
		
		status = xmlMsg.validate();
		System.out.println(status);
		
		System.out.println(xmlMsg.getData().asXML());
		
	}
	
	
}
