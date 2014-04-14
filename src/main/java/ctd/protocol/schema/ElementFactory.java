package ctd.protocol.schema;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ctd.controller.exception.ControllerException;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.constans.DataTypes;
import ctd.protocol.schema.constans.ElementType;
import ctd.protocol.schema.controller.MetaDictionaryController;
import ctd.protocol.schema.controller.SegmentController;
import ctd.protocol.schema.exception.SchemaException;
import ctd.protocol.schema.support.Attribute;
import ctd.protocol.schema.support.Entry;
import ctd.protocol.schema.support.AnyEntry;
import ctd.protocol.schema.support.Meta;
import ctd.protocol.schema.support.container.Dataset;
import ctd.protocol.schema.support.container.Message;
import ctd.protocol.schema.support.container.Segment;
import ctd.util.BeanUtils;

public class ElementFactory {
	
	public static Element createElement(org.dom4j.Element define,String pkg){
		Element el = null;
		
		ElementType elementType = findElementType(define);
		switch(elementType){
			case Segment:
			case Dataset:
			case Message:
				el = createSegment(define,pkg,elementType);
				break;
			case Entry:
				el = createEntry(define,pkg);
				break;
			default:
				break;
		}
		return el;
	}
	
	public static Segment createSegment(org.dom4j.Element define){
		return createSegment(define,null,null);
	}
	
	@SuppressWarnings({ "unchecked"})
	public static Segment createSegment(org.dom4j.Element define,String pkg,ElementType elementType){
		Segment s = null;
		elementType = elementType == null ? findElementType(define) : elementType;
		switch(elementType){
			case Segment:
				s = new Segment();
				break;
			case Dataset:
				s = new Dataset();
				break;
			case Message:
				s = new Message();
				break;
			default:
				throw new SchemaException("elementType mismatch:\n"+define.asXML());
		}
		
		List<org.dom4j.Element> els = define.selectNodes("properies/p");
		for(org.dom4j.Element el : els){
			s.setProperty(el.attributeValue("name"), el.getText());
		}
		
		if(pkg == null){
			pkg = s.getProperty("package",String.class);
		}
		
		//extend
		String type = define.attributeValue("type");
		if(!StringUtils.isEmpty(type)){
			try {
				Container c = SegmentController.instance().get(pkg + "." + type);
				//just copy elements,so can't modify the element.
				s.setElements(c.getElements());
				s.setAttributes(c.getAttributes());
				s.setId(c.getId());
				s.setName(c.getName());
			} 
			catch (ControllerException e) {
				throw new SchemaException("type[" + type + "] not support:\n" + define.asXML());
			}
		}
		setupProperties(s,define);
		setupAttributes(s,define,pkg);
		
		List<org.dom4j.Element> ls = (List<org.dom4j.Element>) define.elements("element");
		for(org.dom4j.Element xmlEl : ls){
			s.addElement(createElement(xmlEl,pkg));
		}
		return s;
	}
	
	public static Entry createEntry(org.dom4j.Element define,String pkg){
		String type = define.attributeValue("type","string");
		try {
			Entry entry = null;
			if(DataTypes.isSupportType(type)){
				entry = new Entry(type);
			}
			else{
				if(type.equals("any")){
					entry = new AnyEntry();
				}
				else{
					Meta meta = MetaDictionaryController.instance().findMeta(pkg + "." + type);
					entry = new Entry(meta);
				}
			}
			if(define != null){
				setupProperties(entry,define);
				setupAttributes(entry,define,pkg);
			}
			return entry;
		} 
		catch (ControllerException e) {
			throw new SchemaException("meta[" + type + "] not found:\n" + define.asXML());
		}
		
	}
	
	public static Attribute createAttribute(org.dom4j.Element define,String pkg){
		String type = define.attributeValue("type","string");
		try {
			Attribute attr = null;
			if(DataTypes.isSupportType(type)){
				attr = new Attribute(type);
			}
			else{
				Meta meta = MetaDictionaryController.instance().findMeta(pkg + "." + type);
				attr = new Attribute(meta);
			}
			setupProperties(attr,define);
			
			return attr;
		} 
		catch (ControllerException e) {
			throw new SchemaException("meta[" + type + "] not found:\n" + define.asXML());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setupAttributes(Element el,org.dom4j.Element define,String pkg){
		List<org.dom4j.Element> ls = (List<org.dom4j.Element>) define.elements("attribute");
		for(org.dom4j.Element xmlEl : ls){
			Attribute attr = createAttribute(xmlEl,pkg);
			el.addAttribute(attr);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setupProperties(Node el,org.dom4j.Element define){
		List<org.dom4j.Attribute> attrs =  define.attributes();
		for(org.dom4j.Attribute attr : attrs){
			String nm = attr.getName();
			String val = attr.getText();
			BeanUtils.setProperty(el, nm, val);
		}
		
	}
	
	private static ElementType findElementType(org.dom4j.Element define){
		String tagName = define.getName();
		String type = define.attributeValue("type");
	
		if(tagName.equals("element")){
			if(StringUtils.isEmpty(type)){
				int childs = define.elements().size();
				if(childs == 0){
					return ElementType.Entry;
				}
				else{
					return ElementType.Segment;
				}
			}
			else{
				if(DataTypes.isSupportType(type) || type.equals("any")){
					return ElementType.Entry;
				}
				else{
					if(StringUtils.startsWith(type, "meta.")){
						return ElementType.Entry;
					}
					else if(StringUtils.startsWith(type, "segment.")){
						return ElementType.Segment;
					}
					else if(StringUtils.startsWith(type, "dataset.")){
						return ElementType.Dataset;
					}
					else if(StringUtils.startsWith(type, "message.")){
						return ElementType.Message;
					}
					else{
						String nsp = define.getQName().getNamespacePrefix();
						if(nsp != null){
							if(nsp.equals("meta")){
								return ElementType.Entry;
							}
							if(nsp.equals("segment")){
								return ElementType.Segment;
							}
							if(nsp.equals("dataset")){
								return ElementType.Dataset;
							}
							if(nsp.equals("message")){
								return ElementType.Message;
							}
						}
						
					}
				}
			}
		}//if(tagName.equals("element")){
		
		if(tagName.equals("segment")){
			return ElementType.Segment;
		}
		
		if(tagName.equals("dataset")){
			return ElementType.Dataset;
		}
		
		if(tagName.equals("message")){
			return ElementType.Message;
		}
		throw new SchemaException("element type[" + type + "] not supported:\n"+define.asXML());
	}
	
	
}
