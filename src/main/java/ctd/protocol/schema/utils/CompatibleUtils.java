package ctd.protocol.schema.utils;

import java.util.List;

import ctd.protocol.schema.Container;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.exception.SchemaException;
import ctd.protocol.schema.support.Attribute;

public class CompatibleUtils {
	
	public  static void compatibleCheck(Attribute attr1,Attribute attr2){
		if(!attr1.getType().equals(attr2.getType())){
			throw new SchemaException("attribute[" + attr1.getId() + "] type mismatch.");
		}
		if(attr2.getLength() > attr1.getLength()){
			throw new SchemaException("attribute[" + attr1.getId() + "] length value invaild.");
		}
	}
	
	public  static void compatibleCheck(Element e1,Element e2){
		if(!e1.getType().equals(e2.getType())){
			throw new SchemaException("element[" + e1.getId() + "] type mismatch.");
		}
		if(e2.getLength() > e1.getLength()){
			throw new SchemaException("element[" + e1.getId() + "] length value invaild.");
		}
		if(e1.getMinoccurs() > e2.getMinoccurs()){
			throw new SchemaException("element[" + e1.getId() + "] minoccirs must >=" + e1.getMinoccurs());
		}
		if(e2.getMaxoccurs() > e1.getMaxoccurs()){
			throw new SchemaException("element[" + e1.getId() + "] maxoccurs must <=" + e1.getMinoccurs());
		}
		if(e1.isFixedLength() && !e2.isFixedLength()){
			throw new SchemaException("element[" + e1.getId() + "] fixedLength must be true");
		}
		if(e1 instanceof Container){
			Container c1 = (Container)e1;
			Container c2 = (Container)e2;
			List<Element> ls = c1.getElements();
			for(Element child : ls){
				compatibleCheck(child,c2.getElementAt(child.getPosition()));
			}
		}
	}

}
