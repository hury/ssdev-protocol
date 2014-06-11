package ctd.protocol.schema.controller.loader;


import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import ctd.controller.exception.ControllerException;
import ctd.controller.support.AbstractConfigurableLoader;
import ctd.protocol.schema.ElementFactory;
import ctd.protocol.schema.support.container.Segment;


public class SegmentLocalLoader extends AbstractConfigurableLoader<Segment> {

	@Override
	public Segment createInstanceFormDoc(String id, Document doc,long lastModi) throws ControllerException {
		Element root = doc.getRootElement();
		if(root == null){
			throw new ControllerException(ControllerException.PARSE_ERROR,"root element missing.");
		}
		try{
			Segment s =  ElementFactory.createSegment(root);
			if(StringUtils.isEmpty(s.getId())){
				s.setId(StringUtils.substringAfterLast(id, "."));
			}
			s.setLastModify(lastModi);
			return s;
		}
		catch(Exception e){
			throw new ControllerException(e,ControllerException.PARSE_ERROR,"metaDictionary[" + id + "] init unknow error:"+ e.getMessage());
		}
		
	}

	

}
