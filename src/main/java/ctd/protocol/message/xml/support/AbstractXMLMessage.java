package ctd.protocol.message.xml.support;

import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import ctd.protocol.schema.Container;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.order.Order;
import ctd.protocol.schema.support.Attribute;
import ctd.protocol.message.Message;
import ctd.protocol.message.exception.MessageException;
import ctd.protocol.message.exception.ValidateException;
import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.util.converter.ConversionUtils;


public abstract class AbstractXMLMessage implements XMLMessage{
	protected Element el;
	private XMLMessage parent;
	private int position;

	public AbstractXMLMessage(Element el){
		this.el = el;
	}

	@Override
	public Element getElement(){
		return el;
	}
	
	protected org.dom4j.Element createXMLElement(Element el){
		org.dom4j.Element xmlEl = DocumentHelper.createElement(el.getId());
		List<Attribute> attrs = el.getAttributes();
		for(Attribute attr : attrs){
			Object v = attr.getDefaultValue();
			if(v != null){
				xmlEl.addAttribute(attr.getId(), el.toDisplayValue(v));
			}
		}
		
		if(el instanceof Container){
			Container c = (Container)el;
			if(c.getOrder() != Order.CHOOSE){
				List<Element> childs = c.getElements();
				for(Element child : childs){
					if((child.isRequire() && child.getRepetition() == 1) || child.getDefaultValue() != null){
						xmlEl.add(createXMLElement(child));
					}
				}
			}
		}
		else{
			Object v = el.getDefaultValue();
			if(v != null){
				xmlEl.setText(el.toDisplayValue(v));
			}
		}
		return xmlEl;
	}
	
	@Override
	public void parseString(String msg) {
		try {
			org.dom4j.Element data = DocumentHelper.parseText(msg).getRootElement();
			addData(data);
		} 
		catch (DocumentException e) {
			throw new MessageException("parse xml error:\n" + msg,e);
		}
	}
	
	@Override
	public void setAttribute(String id,Object v){
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			data = createXMLElement(el);
			addData(data);
		}
		Attribute attr = el.getAttribute(id);
		if(attr != null){
			data.addAttribute(id, attr.toDisplayValue(v));
		}
		else{
			throw new MessageException("element[" + el.getId() + "] attribute[" + id +"] not defined.");
		}
	}
	
	@Override
	public Object getAttribute(String id){
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			return null;
		}
		return data.attributeValue(id);
	}
	
	@Override
	public org.dom4j.Element getData() {
		return getCurrentData();
	}
	
	protected org.dom4j.Element getCurrentData() {
		return null;
	}
	
	@Override
	public void setValue(Object v) {
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			data = createXMLElement(el);
			addData(data);
		}
		data.setText(ConversionUtils.convert(v, String.class));
	}
	
	@Override
	public Object getValue() {
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			return null;
		}
		return el.parseValue(data.getText());
	}
	
	@Override
	public XMLMessage getParent() {
		return parent;
	}
	
	@Override
	public void setParent(XMLMessage parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean isRoot(){
		return this.parent == null;
	}
	
	@Override
	public Message<org.dom4j.Element> child(int i) {
		return null;
	}

	@Override
	public Message<org.dom4j.Element> child(String id) {
		return null;
	}
	
	@Override
	public void nextGroup() {
	}
	
	@Override
	public int getGroupIndex(){
		return 0;
	}

	@Override
	public void toGroup(int index) {
		
	}
	
	@Override
	public int getGroupCount(){
		return 1;
	}
	
	@Override
	public ValidateStatus validate() throws ValidateException{
		
		if(el.isRequire() || isRoot()){
			org.dom4j.Element data = getCurrentData();
			if(data == null){
				return ValidateStatus.buildStatus(ErrorType.ElementIsRequire, ">=1",0,this);
			}
		}
		List<Attribute> attrs = el.getAttributes();
		for(Attribute attr : attrs){
			String attrId = attr.getId();
			ValidateStatus status = attr.validate(getAttribute(attrId));
			if(status.isNotOK()){
				status.setSource(this);
				status.setPosition("@" + attrId);
				return status;
			}
		}
		return ValidateStatus.STATUS_OK;
	}
	
	@Override
	public void validateAll(List<ValidateStatus> status){
		
	}
	
	@Override
	public int getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	
	
}
