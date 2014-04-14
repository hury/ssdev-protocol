package test.protocol.dataset;

import ctd.controller.exception.ControllerException;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.support.Attribute;
import ctd.protocol.schema.support.container.Dataset;
import ctd.protocol.schema.support.container.Segment;
import junit.framework.TestCase;

public class DatasetTester extends TestCase {
	public void testDataset1() throws ControllerException{
		Dataset s = (Dataset)SegmentController.instance().get("test.protocol.dataset.Dataset1");
		assertNotNull(s);
		assertEquals(2, s.getChildElementsCount());
		assertEquals("date",s.getElementAt(1).getType());
		
		Segment s1 = (Segment) s.getElementById("PT");
		assertNotNull(s1);
		assertEquals("meta.MetaSet1.PN", s1.getElementAt(0).getType());
		assertTrue(s1.isRequire());
		
		assertEquals("gb.gender",s1.getElementAt(3).getTable());
		assertEquals("sex",s1.getElementAt(3).getId());
		
		assertEquals(5,s1.getChildElementsCount());
		assertEquals("ID",s1.getElementAt(2).getId());
		assertTrue(s1.getElementAt(2).isRequire());
		
		Attribute attr = s.getAttribute("versionId");
		assertNotNull(attr);
		assertEquals("°æ±¾ºÅ",attr.getName());
		
	}
	
	public void testDataset2() throws ControllerException{
		Dataset s = (Dataset)SegmentController.instance().get("test.protocol.dataset.Dataset2");
		assertNotNull(s);
		assertEquals(2, s.getChildElementsCount());
		assertEquals("date",s.getElementAt(1).getType());
		
		Segment s1 = (Segment) s.getElementById("PT");
		assertNotNull(s1);
		assertEquals("meta.MetaSet1.PN", s1.getElementAt(0).getType());
		assertFalse(s1.isRequire());
		
		assertEquals("gb.gender",s1.getElementAt(3).getTable());
		assertEquals("sex",s1.getElementAt(3).getId());
		
		assertEquals(5,s1.getChildElementsCount());
		assertEquals("ID",s1.getElementAt(2).getId());
		assertTrue(s1.getElementAt(2).isRequire());
		
		Attribute attr = s.getAttribute("versionId");
		assertNotNull(attr);
		assertEquals("°æ±¾ºÅ",attr.getName());
		
	}
}
