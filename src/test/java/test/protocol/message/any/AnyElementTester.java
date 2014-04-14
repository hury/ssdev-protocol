package test.protocol.message.any;


import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.support.AnyEntry;
import junit.framework.TestCase;

public class AnyElementTester extends TestCase {
	public void testAny(){
		AnyEntry el = new AnyEntry();
		el.setId("namecard");
		XMLMessage message = XMLMessageFactory.createMessage(el);
		assertNotNull(message);
		message.child("name").setValue("sean");
		message.setAttribute("version", "s304"); 

		System.out.println(message.getData().asXML());
		
		ValidateStatus status = message.validate();
		System.out.println(status);
	}
}
