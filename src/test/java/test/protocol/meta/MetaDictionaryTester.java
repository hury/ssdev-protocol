package test.protocol.meta;

import java.util.Date;

import ctd.controller.exception.ControllerException;
import ctd.protocol.schema.controller.MetaDictionary;
import ctd.protocol.schema.controller.MetaDictionaryController;
import ctd.protocol.schema.support.Meta;
import junit.framework.TestCase;

public class MetaDictionaryTester extends TestCase {
	public void testController() throws ControllerException{
		MetaDictionary dic = MetaDictionaryController.instance().get("test.protocol.meta.MetaSet1");
		assertNotNull(dic);
		Meta meta = dic.findMeta("PN");
		assertNotNull(meta);
		assertEquals("string", meta.getType());
	}
	
	public void testMyDateMeta() throws ControllerException{
		MetaDictionary dic = MetaDictionaryController.instance().get("test.protocol.meta.MetaSet2");
		assertNotNull(dic);
		Meta meta = dic.findMeta("myDate");
		assertNotNull(meta);
		assertEquals("date", meta.getType());
		System.out.println(meta.toDisplayValue(new Date()));
	}
}
